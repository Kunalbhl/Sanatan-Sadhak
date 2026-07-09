import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

# Extract Sacred Services item block
services_pattern = r"(        // Category Options Tile Grid \(Sacred Services\)\n        item \{.*?\}             \}\n        \})"
services_match = re.search(services_pattern, content, flags=re.DOTALL)
services_code = services_match.group(1)

# Extract Panchang item block
panchang_pattern = r"(        // 2\. Today's Panchang\n        item \{.*?\}             \}\n        \})"
panchang_match = re.search(panchang_pattern, content, flags=re.DOTALL)
panchang_code = panchang_match.group(1)

# Remove Profile from services
services_code = services_code.replace('Triple(if (isEng) "Account / Profile" else "साधक खाता", Icons.Default.Person, "Profile")', '')
# clean up commas
services_code = services_code.replace(',\n                \n', '\n')

# Swap them
content = content.replace(services_code, "%%SERVICES%%")
content = content.replace(panchang_code, services_code)
content = content.replace("%%SERVICES%%", panchang_code)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
