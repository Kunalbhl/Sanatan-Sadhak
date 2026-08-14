import sys
content = sys.stdin.read()
target = """                setAudioStreamType(android.media.AudioManager.STREAM_MUSIC)"""
replacement = """                setAudioAttributes(
                    android.media.AudioAttributes.Builder()
                        .setContentType(android.media.AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(android.media.AudioAttributes.USAGE_MEDIA)
                        .build()
                )"""
if target in content:
    with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
        f.write(content.replace(target, replacement))
    print("Fixed audio in SadhakViewModel")
else:
    print("Target not found")
