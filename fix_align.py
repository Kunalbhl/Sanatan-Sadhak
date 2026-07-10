with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "r") as f:
    text = f.read()
target = """        // Scroll to top button
        if (scrollState.value > 150) {
            ScrollToTopButton(
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(0)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }"""
replacement = """        // Scroll to top button
        if (scrollState.value > 150) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                ScrollToTopButton(
                    onClick = {
                        coroutineScope.launch {
                            scrollState.animateScrollTo(0)
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }"""
text = text.replace(target, replacement)
with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "w") as f:
    f.write(text)

with open("app/src/main/java/com/example/ui/screens/CounterView.kt", "r") as f:
    text = f.read()
target = """        // Global Scroll To Top Button
        if (listState.firstVisibleItemIndex > 2) {
            com.example.ui.components.ScrollToTopButton(
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }"""
replacement = """        // Global Scroll To Top Button
        if (listState.firstVisibleItemIndex > 2) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                com.example.ui.components.ScrollToTopButton(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }"""
text = text.replace(target, replacement)
with open("app/src/main/java/com/example/ui/screens/CounterView.kt", "w") as f:
    f.write(text)
