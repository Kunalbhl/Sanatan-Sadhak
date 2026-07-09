import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

replacement = """fun HomeScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_RESUME) {
                viewModel.loadDailyPanchangAndThought()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    
    var showVerifiedPrompt by remember { mutableStateOf(true) }"""

content = content.replace("""fun HomeScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    var showVerifiedPrompt by remember { mutableStateOf(true) }""", replacement)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
