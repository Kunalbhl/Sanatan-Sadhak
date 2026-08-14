import re
with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

# Replace url in DevotionalTrack 1
text = re.sub(r'url = "https://archive.org/download/MahaMrityunjayaMantra108Times/Maha%20Mrityunjaya%20Mantra%20-%20108%20Times.mp3"', 'url = "https://upload.wikimedia.org/wikipedia/commons/4/47/07-RUDRA_PRASNAM-MANTRA_MANJUSHA.ogg"', text)

# Replace url in DevotionalTrack 2
text = re.sub(r'url = "https://archive.org/download/gayatrimantra108times_201910/Gayatri%20Mantra%20108%20Times.mp3"', 'url = "https://upload.wikimedia.org/wikipedia/commons/e/e4/Gayatri_Mantra_as_it_is_2021_02_13_05_48_%2B.wav"', text)

# Replace url in DevotionalTrack 3
text = re.sub(r'url = "https://archive.org/download/HanumanChalisa_201611/Hanuman%20Chalisa.mp3"', 'url = "https://upload.wikimedia.org/wikipedia/commons/e/ef/03-GANAPATHI_SUKTHA-MANTRA_MANJUSHA.ogg"', text)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)
