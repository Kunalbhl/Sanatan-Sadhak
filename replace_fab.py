with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    text = f.read()

target = """        // Global Scroll To Top Button
        if (scrollState.value > 150) {
            com.example.ui.components.ScrollToTopButton(
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(0)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp)
            )
        }"""

replacement = """        // Global Scroll To Top Button
        if (scrollState.value > 150) {
            androidx.compose.material3.FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(0)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha=0.3f),
                contentColor = androidx.compose.ui.graphics.Color.White
            ) {
                Icon(Icons.Default.ArrowUpward, contentDescription = "Scroll to top")
            }
        }"""

text = text.replace(target, replacement)
with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(text)

