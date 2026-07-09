import re

with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()

bad = """@Composable
fun SanatanSadhakLogo(modifier: Modifier = Modifier) {
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
                style = Stroke(width = 1.5.dp.toPx())
            )
            
            // Flame Glow on top
            drawCircle(
                color = Color(0xFFFF9800).copy(alpha = 0.8f),
                radius = 3.dp.toPx(),
                center = Offset(w / 2, h * 0.15f)
            )
            drawCircle(
                color = Color(0xFFFFEB3B),
                radius = 1.5.dp.toPx(),
                center = Offset(w / 2, h * 0.15f)
            )
        }
        
        // Golden OM symbol overlay in the center
        Text(
            text = "ॐ",
            color = Color(0xFFFFC107),
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            modifier = Modifier.offset(y = 1.dp)
        )
    }
}"""

good = """@Composable
fun SanatanSadhakLogo(modifier: Modifier = Modifier) {
    androidx.compose.foundation.Image(
        painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.app_logo),
        contentDescription = "App Logo",
        contentScale = androidx.compose.ui.layout.ContentScale.Crop,
        modifier = modifier
            .size(36.dp)
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colorScheme.tertiary, CircleShape)
    )
}"""

content = content.replace(bad, good)

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)
