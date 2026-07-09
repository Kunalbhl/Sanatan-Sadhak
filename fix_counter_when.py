import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

bad = """        when (selectedToolSubTab) {
            "Mantras" -> MantrasView(viewModel, isEng)
            "Timer" -> TimerView(viewModel, isEng)
            "KarmaLog" -> KarmaLogView(viewModel, isEng)
            "Gratitude" -> GratitudeView(viewModel, isEng)
            "Counter" -> CounterView(isEng)
        }"""
good = """        when (selectedToolSubTab) {
            "Mantras" -> MantraLibraryView(viewModel, isEng)
            "Timer" -> MeditationTimerView(isEng)
            "KarmaLog" -> KarmaTrackerView(viewModel, isEng)
            "Gratitude" -> GratitudeJournalView(viewModel, isEng)
            "Counter" -> CounterView(isEng)
        }"""
content = content.replace(bad, good)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
