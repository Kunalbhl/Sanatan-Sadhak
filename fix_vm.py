import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    lines = f.readlines()

out = []
skip = False
for i, line in enumerate(lines):
    if "fun upvoteComment(comment: com.example.data.PostComment)" in line:
        # Check if we already have it in out
        if any("fun upvoteComment" in l for l in out):
            skip = True
    elif skip and line.strip() == "}":
        # Keep skipping until we close the function? Actually regex replacement is safer.
        pass

