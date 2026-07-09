import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

# Extract the PrayerData block
prayer_data_match = re.search(r'^data class PrayerData.*?val allPrayersList = listOf\(.*?\)\n\n', content, flags=re.DOTALL)
if prayer_data_match:
    prayer_data = prayer_data_match.group(0)
    # Remove it from the top
    content = content[len(prayer_data):]
    
    # Insert it after the imports
    last_import = content.rfind("import ")
    if last_import != -1:
        end_of_imports = content.find("\n", last_import) + 1
        content = content[:end_of_imports] + "\n" + prayer_data + content[end_of_imports:]
    else:
        # Fallback to after package
        package_end = content.find("\n") + 1
        content = content[:package_end] + "\n" + prayer_data + content[package_end:]
        
    with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
        f.write(content)
