import re

with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()

replacement = """
    val primaryColor = MaterialTheme.colorScheme.primary
    Box(
        modifier = modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(Color(0xFF4A0E17)) // Deep wine/maroon
            .border(2.dp, MaterialTheme.colorScheme.tertiary, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        // Draw Trishula (Trident) using Canvas
        Canvas(modifier = Modifier.fillMaxSize().padding(4.dp)) {
            val w = size.width
            val h = size.height
            
            // Central prong
            drawLine(
                color = primaryColor,
                start = Offset(w / 2, h * 0.15f),
                end = Offset(w / 2, h * 0.85f),
                strokeWidth = 2.dp.toPx()
            )
            
            // Left curve
            val leftPath = Path().apply {
                moveTo(w / 2, h * 0.7f)
                quadraticTo(w * 0.2f, h * 0.65f, w * 0.25f, h * 0.3f)
            }
            drawPath(
                path = leftPath,
                color = primaryColor,
                style = Stroke(width = 1.5.dp.toPx())
            )
            
            // Right curve
            val rightPath = Path().apply {
                moveTo(w / 2, h * 0.7f)
                quadraticTo(w * 0.8f, h * 0.65f, w * 0.75f, h * 0.3f)
            }
            drawPath(
                path = rightPath,
                color = primaryColor,
"""
# Need to find the exact block and replace
content = re.sub(r'Box\(\n\s*modifier = modifier\n\s*\.size\(36\.dp\)\n\s*\.clip\(CircleShape\)\n\s*\.background\(Color\(0xFF4A0E17\)\) // Deep wine/maroon\n\s*\.border\(2\.dp, MaterialTheme\.colorScheme\.tertiary, CircleShape\),\n\s*contentAlignment = Alignment\.Center\n\s*\) \{\n\s*// Draw Trishula \(Trident\) using Canvas\n\s*Canvas\(modifier = Modifier\.fillMaxSize\(\)\.padding\(4\.dp\)\) \{\n\s*val w = size\.width\n\s*val h = size\.height\n\s*// Central prong\n\s*drawLine\(\n\s*color = MaterialTheme\.colorScheme\.primary,\n\s*start = Offset\(w / 2, h \* 0\.15f\),\n\s*end = Offset\(w / 2, h \* 0\.85f\),\n\s*strokeWidth = 2\.dp\.toPx\(\)\n\s*\)\n\s*// Left curve\n\s*val leftPath = Path\(\)\.apply \{\n\s*moveTo\(w / 2, h \* 0\.7f\)\n\s*quadraticTo\(w \* 0\.2f, h \* 0\.65f, w \* 0\.25f, h \* 0\.3f\)\n\s*\}\n\s*drawPath\(\n\s*path = leftPath,\n\s*color = MaterialTheme\.colorScheme\.primary,\n\s*style = Stroke\(width = 1\.5\.dp\.toPx\(\)\)\n\s*\)\n\s*// Right curve\n\s*val rightPath = Path\(\)\.apply \{\n\s*moveTo\(w / 2, h \* 0\.7f\)\n\s*quadraticTo\(w \* 0\.8f, h \* 0\.65f, w \* 0\.75f, h \* 0\.3f\)\n\s*\}\n\s*drawPath\(\n\s*path = rightPath,\n\s*color = MaterialTheme\.colorScheme\.primary,', replacement, content)

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)
