import re

with open("app/src/main/java/com/example/ui/components/CommonComponents.kt", "r") as f:
    content = f.read()

replacement = """@Composable
fun DiyaDecoration(modifier: Modifier = Modifier, size: Dp = 32.dp, color: Color = MaterialTheme.colorScheme.primary) {
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary

    Canvas(modifier = modifier.size(size)) {
        val width = size.toPx()
        val height = size.toPx()
        
        // Draw Flame first (higher up)
        val flamePath = Path().apply {
            moveTo(width * 0.5f, height * 0.45f)
            quadraticTo(width * 0.35f, height * 0.28f, width * 0.5f, height * 0.05f)
            quadraticTo(width * 0.65f, height * 0.28f, width * 0.5f, height * 0.45f)
        }
        drawPath(
            path = flamePath,
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFFFEA7A), tertiaryColor, Terracotta),
                center = androidx.compose.ui.geometry.Offset(width * 0.5f, height * 0.25f),
                radius = width * 0.25f
            )
        )
        // Draw Diya Bowl
        val bowlPath = Path().apply {
            moveTo(width * 0.15f, height * 0.55f)
            cubicTo(
                width * 0.15f, height * 0.9f,
                width * 0.85f, height * 0.9f,
                width * 0.85f, height * 0.55f
            )
            lineTo(width * 0.58f, height * 0.52f)
            quadraticTo(width * 0.5f, height * 0.45f, width * 0.42f, height * 0.52f)
            close()
        }
        drawPath(
            path = bowlPath,
            brush = Brush.linearGradient(
                colors = listOf(primaryColor, Terracotta, secondaryColor)
            )
        )
    }
}
"""

content = re.sub(r'@Composable\nfun DiyaDecoration\(modifier: Modifier = Modifier, size: Dp = 32\.dp, color: Color = MaterialTheme\.colorScheme\.primary\) \{.*?(?=@Composable\nfun SacredCard)', replacement, content, flags=re.DOTALL)

# And fix SacredCard which might be broken too if it has a dynamic color as default arg
content = re.sub(r'backgroundColor: Color = MaterialTheme\.colorScheme\.surface,', 'backgroundColor: Color = Color.Unspecified,', content)
content = re.sub(r'borderColor: Color = WarmBorder,', 'borderColor: Color = Color.Unspecified,', content)
content = content.replace('colors = CardDefaults.cardColors(containerColor = backgroundColor),', 'colors = CardDefaults.cardColors(containerColor = if(backgroundColor == Color.Unspecified) MaterialTheme.colorScheme.surface else backgroundColor),')
content = content.replace('.border(1.dp, borderColor, RoundedCornerShape(16.dp)),', '.border(1.dp, if(borderColor == Color.Unspecified) WarmBorder else borderColor, RoundedCornerShape(16.dp)),')

with open("app/src/main/java/com/example/ui/components/CommonComponents.kt", "w") as f:
    f.write(content)

