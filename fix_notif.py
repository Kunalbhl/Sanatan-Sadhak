import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

notif_str = """
    private val _notifications = MutableStateFlow<List<com.example.data.AppNotification>>(emptyList())
    val notifications: StateFlow<List<com.example.data.AppNotification>> = _notifications
"""

text = text.replace(
    "    private val _hasNewNotifications = MutableStateFlow(false)",
    notif_str + "\n    private val _hasNewNotifications = MutableStateFlow(false)"
)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)

