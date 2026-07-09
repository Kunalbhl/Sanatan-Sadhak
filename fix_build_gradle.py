import re

with open("app/build.gradle.kts", "r") as f:
    content = f.read()

# We need to add buildConfigField("String", "PANCHANG_API_KEY", "...")
# Let's insert it inside defaultConfig
insert_str = '\n        buildConfigField("String", "PANCHANG_API_KEY", "\\\"\\\"")\n'
content = content.replace("defaultConfig {", "defaultConfig {" + insert_str)

# Enable buildFeatures { buildConfig = true }
if "buildConfig = true" not in content:
    content = content.replace("buildFeatures {", "buildFeatures {\n        buildConfig = true")

with open("app/build.gradle.kts", "w") as f:
    f.write(content)
