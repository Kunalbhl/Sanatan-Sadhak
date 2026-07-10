with open("app/src/main/java/com/example/ui/components/CommonComponents.kt", "r") as f:
    text = f.read()

target = """@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .size(44.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .border(1.5.dp, MaterialTheme.colorScheme.tertiary, CircleShape)
            .clickable { onClick() }
            .testTag("scroll_to_top_fab"),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.ArrowUpward,
            contentDescription = "Scroll to Top",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }
}"""

replacement = """@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .testTag("scroll_to_top_fab"),
        containerColor = MaterialTheme.colorScheme.primary.copy(alpha=0.3f),
        contentColor = androidx.compose.ui.graphics.Color.White,
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Default.ArrowUpward,
            contentDescription = "Scroll to Top"
        )
    }
}"""
text = text.replace(target, replacement)
with open("app/src/main/java/com/example/ui/components/CommonComponents.kt", "w") as f:
    f.write(text)

