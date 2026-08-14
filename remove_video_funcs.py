import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

# I will use a literal script to rewrite the whole file line by line to remove the video methods
lines = text.split('\n')
new_lines = []

skip = False
for line in lines:
    if "fun autoSyncYoutubeVideosIfNeeded()" in line:
        skip = True
    elif "fun syncYoutubeVideos(onComplete: (Int) -> Unit = {}) {" in line:
        skip = True
    elif "fun updateBhaktiVideo(video: BhaktiVideo) {" in line:
        skip = True
    elif "fun deleteBhaktiVideo(id: Int) {" in line:
        skip = True
    elif "fun addBhaktiVideo(title: String, description: String, category: String, videoId: String) {" in line:
        skip = True
    elif "private val _isSyncingVideos = MutableStateFlow(false)" in line:
        skip = True
        continue
    elif "val isSyncingVideos: StateFlow<Boolean> = _isSyncingVideos" in line:
        continue
    
    if skip:
        # Stop skipping if we encounter a new function that is not these
        if line.startswith("    fun ") and "autoSyncYoutubeVideosIfNeeded" not in line and "syncYoutubeVideos" not in line and "updateBhaktiVideo" not in line and "deleteBhaktiVideo" not in line and "addBhaktiVideo" not in line:
            skip = False
            new_lines.append(line)
    else:
        new_lines.append(line)

text = '\n'.join(new_lines)
with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)

