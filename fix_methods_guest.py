import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

def inject_check(method_sig, content):
    rep = method_sig + "\n        if (!requireLoginAction()) return"
    return content.replace(method_sig, rep)

content = inject_check('fun addFavoriteMantra(title: String, content: String, youtubeUrl: String) {', content)
content = inject_check('fun addKarmaLog(log: String, isEnglish: Boolean) {', content)
content = inject_check('fun addGratitudeEntry(entry: String, isEnglish: Boolean) {', content)
content = inject_check('fun addCommunityPost(postText: String) {', content)

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content)

