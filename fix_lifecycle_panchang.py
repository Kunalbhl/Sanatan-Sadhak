import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

replacement = """@Composable
fun HomeScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsStateWithLifecycle()
    val userRole by viewModel.userRole.collectAsStateWithLifecycle()

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

    val tithiEn by viewModel.tithiEn.collectAsStateWithLifecycle()"""

content = content.replace("""@Composable
fun HomeScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsStateWithLifecycle()
    val userRole by viewModel.userRole.collectAsStateWithLifecycle()

    val tithiEn by viewModel.tithiEn.collectAsStateWithLifecycle()""", replacement)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
