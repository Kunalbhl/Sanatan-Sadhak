import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    text = f.read()

text = re.sub(r"    fun upvotePost\(post: CommunityPost\) \{\s*viewModelScope\.launch \{\s*repository\.updatePost\(post\.copy\(upvotes = post\.upvotes \+ 1\)\)\s*statsLikesCount\.value \+= 1\s*\}\s*\}", "", text)
text = re.sub(r"    fun downvotePost\(post: CommunityPost\) \{\s*viewModelScope\.launch \{\s*repository\.updatePost\(post\.copy\(downvotes = post\.downvotes \+ 1\)\)\s*statsLikesCount\.value \+= 1\s*\}\s*\}", "", text)
text = re.sub(r"    fun togglePinPost\(post: CommunityPost\) \{\s*viewModelScope\.launch \{\s*repository\.updatePost\(post\.copy\(isPinned = !post\.isPinned\)\)\s*\}\s*\}", "", text)

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(text)
print("Regex replace done")
