import re

with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()

replacement = """        items.forEach { item ->
            val isSelected = currentScreen == item.route
            val scale by androidx.compose.animation.core.animateFloatAsState(
                targetValue = if (isSelected) 1.2f else 1.0f,
                animationSpec = androidx.compose.animation.core.spring(
                    dampingRatio = androidx.compose.animation.core.Spring.DampingRatioMediumBouncy,
                    stiffness = androidx.compose.animation.core.Spring.StiffnessLow
                )
            )
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(20.dp).androidx.compose.ui.graphics.graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                    )
                },"""

content = content.replace("""        items.forEach { item ->
            val isSelected = currentScreen == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(20.dp)
                    )
                },""", replacement)

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)
