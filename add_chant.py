with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    text = f.read()

target = """    // Notifications toggle state
    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled"""

replacement = """    // Notifications toggle state
    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled

    private val _backgroundChantEnabled = MutableStateFlow(false)
    val backgroundChantEnabled: StateFlow<Boolean> = _backgroundChantEnabled
    private val _backgroundChantVolume = MutableStateFlow(0.3f)
    val backgroundChantVolume: StateFlow<Float> = _backgroundChantVolume

    fun toggleBackgroundChant() {
        _backgroundChantEnabled.value = !_backgroundChantEnabled.value
    }
    fun setBackgroundChantVolume(vol: Float) {
        _backgroundChantVolume.value = vol
    }"""

text = text.replace(target, replacement)
with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(text)

