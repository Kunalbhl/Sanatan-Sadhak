import sys
with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    text = f.read()

target = """                    com.example.SanatanSadhakLogo(modifier = Modifier
                        .size(260.dp)
                        .graphicsLayer(alpha = alphaBlink)
                    )"""

replacement = """                    com.example.SanatanSadhakLogo(
                        modifier = Modifier.padding(16.dp),
                        logoSize = 120.dp,
                        isAnimated = true
                    )"""

if target in text:
    text = text.replace(target, replacement)
    with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
        f.write(text)
    print("Logo call updated")
else:
    print("Logo call Target not found")
