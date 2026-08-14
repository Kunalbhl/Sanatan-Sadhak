import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

new_tracks = '''val devotionalTracks = listOf(
    DevotionalTrack(id = 1, titleEn = "Mahamrityunjaya Mantra (108 Chants)", titleHi = "महामृत्युंजय मंत्र (108 जाप)", descriptionEn = "Sacred sound vibrations of Lord Shiva for protection and healing.", descriptionHi = "मोक्ष और आरोग्यता के लिए भगवान शिव का महामंत्र जाप।", artistEn = "Aum Vedic Chants", artistHi = "ॐ वैदिक साधक", url = "https://upload.wikimedia.org/wikipedia/commons/4/47/07-RUDRA_PRASNAM-MANTRA_MANJUSHA.ogg"),
    DevotionalTrack(id = 2, titleEn = "Gayatri Mantra Chanting", titleHi = "गायत्री मंत्र साधना", descriptionEn = "Universal cosmic sound for divine wisdom, intellect, and light.", descriptionHi = "दिव्य बुद्धि और प्रकाश के लिए सार्वभौमिक ब्रह्मांडीय मंत्र ध्यान।", artistEn = "Sadhana Choir", artistHi = "साधना मंडल", url = "https://upload.wikimedia.org/wikipedia/commons/e/e4/Gayatri_Mantra_as_it_is_2021_02_13_05_48_%2B.wav"),
    DevotionalTrack(id = 3, titleEn = "Hanuman Chalisa", titleHi = "श्री हनुमान चालीसा", descriptionEn = "Peaceful recitations for strength and courage.", descriptionHi = "बल, बुद्धि और विद्या के लिए हनुमान चालीसा का पाठ।", artistEn = "Vrindavan Devotees", artistHi = "साधना उपासक", url = "https://upload.wikimedia.org/wikipedia/commons/e/ef/03-GANAPATHI_SUKTHA-MANTRA_MANJUSHA.ogg"),
    DevotionalTrack(id = 4, titleEn = "Shiva Tandav Stotram", titleHi = "शिव तांडव स्तोत्रम्", descriptionEn = "Powerful stotram by Ravana praising Lord Shiva.", descriptionHi = "भगवान शिव की स्तुति में रावण द्वारा रचित शक्तिशाली स्तोत्र।", artistEn = "Traditional", artistHi = "परंपरागत", url = "https://upload.wikimedia.org/wikipedia/commons/1/13/Shiva_Tandava_Stotram.ogg"),
    DevotionalTrack(id = 5, titleEn = "Vishnu Sahasranamam", titleHi = "विष्णु सहस्रनाम", descriptionEn = "Thousand names of Lord Vishnu.", descriptionHi = "भगवान विष्णु के एक हजार नाम।", artistEn = "Traditional", artistHi = "परंपरागत", url = "https://upload.wikimedia.org/wikipedia/commons/a/ac/Vishnu_Sahasranamam.ogg")
)'''

# Using regex to find the devotionalTracks definition and replace it
# The definition starts with 'val devotionalTracks = listOf(' and ends with ')'
# This might be tricky because of potential multiple lines or brackets.
# A simpler way: replace the entire block between 'val devotionalTracks = listOf(' and ')'

# Regex to match the devotionalTracks block
pattern = r'val devotionalTracks = listOf\(\n([\s\S]*?)\n\)'
text = re.sub(pattern, new_tracks, text)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)
