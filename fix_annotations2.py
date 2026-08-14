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

        content = content.replace('@androidx.compose.material3.ExperimentalMaterial3Api\n@androidx.compose.runtime.Composable', '@OptIn(ExperimentalMaterial3Api::class)\n@Composable')
        
        with open(filepath, 'w') as f:
            f.write(content)

