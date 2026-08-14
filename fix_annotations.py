import os
import re

directories = ["common", "sadhana", "home", "knowledge", "panchang", "community", "video", "profile"]

for d in directories:
    dir_path = os.path.join('app/src/main/java/com/example/ui/screens', d)
    if not os.path.exists(dir_path):
        continue
    for filename in os.listdir(dir_path):
        if not filename.endswith('.kt'):
            continue
        filepath = os.path.join(dir_path, filename)
        with open(filepath, 'r') as f:
            content = f.read()

        # Remove dangling annotations that are by themselves (leftovers)
        # Actually in SharedComponents.kt there are trailing annotations
        content = re.sub(r'(?:@Composable\s*)+$', '', content, flags=re.MULTILINE)
        content = re.sub(r'(?:@OptIn\(ExperimentalMaterial3Api::class\)\s*)+$', '', content, flags=re.MULTILINE)

        # Find all `fun SomeFunction` and prepend `@OptIn(ExperimentalMaterial3Api::class)\n@Composable` if it's missing
        # But wait, we only want to do this for uppercase functions.
        def replacer(match):
            func_decl = match.group(0)
            if not func_decl.strip().startswith('fun '):
                return func_decl
            
            # Check if it already has @Composable right above it.
            # We don't have that context easily, but we can do it by finding `fun [A-Z]` in the content directly.
            pass
            
        # A better way: replace `\nfun [A-Z]` with `\n@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)\n@Composable\nfun [A-Z]`
        # Make sure not to double add
        
        # Strip existing @Composable and @OptIn from the file first to avoid duplicates, then add them cleanly.
        content = re.sub(r'@Composable\s*', '', content)
        content = re.sub(r'@OptIn\(.*?\)\s*', '', content)
        
        # Now add them to all Uppercase functions
        content = re.sub(r'\nfun ([A-Z][a-zA-Z0-9_]*)', r'\n@androidx.compose.material3.ExperimentalMaterial3Api\n@androidx.compose.runtime.Composable\nfun \1', content)
        
        with open(filepath, 'w') as f:
            f.write(content)

