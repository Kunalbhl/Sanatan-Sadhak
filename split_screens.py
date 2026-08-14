import os
import re

def split_file(filepath, out_dir):
    with open(filepath, 'r') as f:
        content = f.read()

    # Extract package and imports
    # Stop before the first `@` or `fun `
    header_match = re.search(r'^(package .*?\n.*?\n)(?=@|fun )', content, re.DOTALL | re.MULTILINE)
    if header_match:
        header = header_match.group(1)
    else:
        header = "package com.example.ui.screens\n\nimport androidx.compose.runtime.*\n"

    parts = re.split(r'\n(?=@(?:OptIn|Composable|Suppress|Preview|JvmStatic))|\n(?=fun )|\n(?=val )|\n(?=var )', content)
    
    if not os.path.exists(out_dir):
        os.makedirs(out_dir)

    for i, part in enumerate(parts):
        if not part.strip():
            continue
        if part.startswith('package ') or part.startswith('import '):
            continue
            
        # extract function name
        match = re.search(r'fun\s+(?:Modifier\.)?([A-Za-z0-9_]+)\s*(?:<.*?>)?\(', part)
        val_match = re.search(r'(?:val|var)\s+([A-Za-z0-9_]+)\s*(=|:)', part)
        
        if match:
            func_name = match.group(1)
        elif val_match:
            func_name = val_match.group(1)
        else:
            func_name = "unknown"

        # simple mapping
        if "Home" in func_name:
            filename = "home/HomeScreen.kt"
        elif "Knowledge" in func_name or "Article" in func_name:
            filename = "knowledge/KnowledgeScreens.kt"
        elif "Calendar" in func_name or "Panchang" in func_name:
            filename = "panchang/CalendarScreens.kt"
        elif "Community" in func_name or "Comment" in func_name:
            filename = "community/CommunityScreens.kt"
        elif "Profile" in func_name or "AppPreferences" in func_name:
            filename = "profile/ProfileScreens.kt"
        elif "Mantra" in func_name or "Sadhana" in func_name or "Meditation" in func_name or "Karma" in func_name or "Gratitude" in func_name or "Bhakti" in func_name or "Aarti" in func_name or "playTempleBellSound" in func_name:
            filename = "sadhana/SadhanaScreens.kt"
        elif "Video" in func_name:
            filename = "video/VideoScreens.kt"
        elif "Chat" in func_name or "CategoryCard" in func_name or "Sankalpa" in func_name or "AccessDenied" in func_name:
            filename = "common/CommonScreens.kt"
        elif "touchFeedback" in func_name or "clickableWithFeedback" in func_name or "ScrollToTopButton" in func_name:
            filename = "common/SharedComponents.kt"
        elif "ShlokaCard" in func_name or "PauseIcon" in func_name or "unknown" in func_name:
            filename = "common/SharedComponents.kt"
        else:
            filename = f"misc/{func_name}.kt"
            
        out_file = os.path.join(out_dir, filename)
        os.makedirs(os.path.dirname(out_file), exist_ok=True)
        
        if not os.path.exists(out_file):
            with open(out_file, 'w') as f:
                f.write(header.strip() + "\n")
        
        with open(out_file, 'a') as f:
            f.write("\n" + part.strip() + "\n")

# Remove previous generated files
import shutil
for dir_name in ["common", "sadhana", "home", "knowledge", "panchang", "community", "video", "profile", "misc"]:
    path = os.path.join('app/src/main/java/com/example/ui/screens', dir_name)
    if os.path.exists(path):
        shutil.rmtree(path)

split_file('app/src/main/java/com/example/ui/screens/AllScreens.kt', 'app/src/main/java/com/example/ui/screens')
split_file('app/src/main/java/com/example/ui/screens/MissingScreens.kt', 'app/src/main/java/com/example/ui/screens')
os.remove('app/src/main/java/com/example/ui/screens/AllScreens.kt')
os.remove('app/src/main/java/com/example/ui/screens/MissingScreens.kt')

