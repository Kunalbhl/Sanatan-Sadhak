import re

with open("app/src/main/java/com/example/ui/theme/Theme.kt", "r") as f:
    content = f.read()

dark_repl = """    surface = TempleDarkSurface,
    surfaceVariant = Color(0xFF3E2D23),
    primaryContainer = Color(0xFF4A2C2A),
    outline = Color(0xFF5A4435),"""
content = content.replace("    surface = TempleDarkSurface,", dark_repl)

light_repl = """    surface = SoftWhite_Light,
    surfaceVariant = SoftGoldBg,
    primaryContainer = LightThoughtBg,
    outline = WarmBorder,"""
content = content.replace("    surface = SoftWhite_Light,", light_repl)

with open("app/src/main/java/com/example/ui/theme/Theme.kt", "w") as f:
    f.write(content)

