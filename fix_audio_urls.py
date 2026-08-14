import sys
content = sys.stdin.read()
target1 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
target2 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"
target3 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"

content = content.replace(target1, "https://archive.org/download/MahaMrityunjayaMantra108Times/Maha%20Mrityunjaya%20Mantra%20-%20108%20Times.mp3")
content = content.replace(target2, "https://archive.org/download/gayatrimantra108times_201910/Gayatri%20Mantra%20108%20Times.mp3")
content = content.replace(target3, "https://archive.org/download/om-chanting-108-times/Om%20Chanting%20108%20Times.mp3")

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(content)
print("Reverted audio urls")
