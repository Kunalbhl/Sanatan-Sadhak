with open('app/src/main/java/com/example/ui/viewmodel/HomeViewModel.kt', 'r') as f:
    content = f.read()

content = content.replace('            "ॐ नमः शिवाय"\n        )', '            "ॐ नमः शिवाय", ""\n        )')
content = content.replace('            "ॐ भूर्भुवः स्वः"\n            )', '            "ॐ भूर्भुवः स्वः", ""\n            )')
content = content.replace('            "ॐ शान्तिः शान्तिः शान्तिः"\n            )', '            "ॐ शान्तिः शान्तिः शान्तिः", ""\n            )')

with open('app/src/main/java/com/example/ui/viewmodel/HomeViewModel.kt', 'w') as f:
    f.write(content)
