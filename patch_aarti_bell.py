import sys
with open("app/src/main/java/com/example/ui/screens/AartiBellScreen.kt", "r") as f:
    text = f.read()

target = """@Composable
fun AartiBellScreen(viewModel: com.example.ui.viewmodel.SadhakViewModel, isEng: Boolean, onBack: () -> Unit) {
    var bellCount by remember { mutableStateOf(0) }
    val haptic = LocalHapticFeedback.current
    val aartiLogs by viewModel.aartiLogs.collectAsState()"""

replacement = """@Composable
fun AartiBellScreen(viewModel: com.example.ui.viewmodel.SadhakViewModel, isEng: Boolean, onBack: () -> Unit) {
    val bellCount by viewModel.currentSessionAartiCount.collectAsState()
    val haptic = LocalHapticFeedback.current
    val aartiLogs by viewModel.aartiSessionLogs.collectAsState()"""

if target in text:
    text = text.replace(target, replacement)

target2 = """            IconButton(onClick = {
                viewModel.addAartiLog(bellCount)
                onBack()
            }) {"""

replacement2 = """            IconButton(onClick = onBack) {"""

if target2 in text:
    text = text.replace(target2, replacement2)

target3 = """                .clickable {
                    bellCount++
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    playTempleBellSound()
                },"""

replacement3 = """                .clickable {
                    viewModel.incrementAartiBell()
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    playTempleBellSound()
                },"""

if target3 in text:
    text = text.replace(target3, replacement3)

target4 = """                items(aartiLogs.size) { index ->
                    val logCount = aartiLogs[index]
                    Text(
                        text = "${index + 1}. ${if (isEng) "You rung bell $logCount times" else "आपने $logCount बार घंटी बजाई"}.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }"""

replacement4 = """                items(aartiLogs.size) { index ->
                    Text(
                        text = "${index + 1}. ${aartiLogs[index]}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }"""

if target4 in text:
    text = text.replace(target4, replacement4)
    with open("app/src/main/java/com/example/ui/screens/AartiBellScreen.kt", "w") as f:
        f.write(text)
    print("AartiBellScreen.kt patched")
else:
    print("Failed to patch AartiBellScreen.kt")
