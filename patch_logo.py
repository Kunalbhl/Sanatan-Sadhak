import sys
with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    text = f.read()

target = """@Composable
fun SanatanSadhakLogo(modifier: Modifier = Modifier) {
    
    val primaryColor = MaterialTheme.colorScheme.primary
    Box(
        modifier = Modifier
            .size(36.dp)
            .then(modifier)
            .clip(CircleShape)
            .background(Color(0xFF4A0E17)) // Deep wine/maroon"""

replacement = """@Composable
fun SanatanSadhakLogo(modifier: Modifier = Modifier, logoSize: androidx.compose.ui.unit.Dp = 36.dp, isAnimated: Boolean = false) {
    
    val infiniteTransition = androidx.compose.animation.core.rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = if (isAnimated) 0.4f else 1f,
        targetValue = 1f,
        animationSpec = androidx.compose.animation.core.infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(800),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ),
        label = "blink"
    )

    val primaryColor = MaterialTheme.colorScheme.primary
    Box(
        modifier = Modifier
            .size(logoSize)
            .then(modifier)
            .alpha(if (isAnimated) alpha else 1f)
            .clip(CircleShape)
            .background(Color(0xFF4A0E17)) // Deep wine/maroon"""

if target in text:
    text = text.replace(target, replacement)
    with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
        f.write(text)
    print("Logo updated")
else:
    print("Logo Target not found")
