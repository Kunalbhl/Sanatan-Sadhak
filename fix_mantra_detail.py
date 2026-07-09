import re

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "r") as f:
    content = f.read()

# We need to pass the PrayerData ID or the whole object.
# But it takes `title`, `content` right now. Let's change it to take a PrayerData id.
# Since we already passed title and content as activePlayingMantraTitle and activePlayingMantraContent,
# let's just change AllScreens to store activeMantraId: String? instead of title/content.

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "w") as f:
    f.write(content)

