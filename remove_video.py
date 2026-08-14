import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

# I will use a literal script to rewrite the whole file line by line to remove the video section
lines = text.split('\n')
new_lines = []

i = 0
while i < len(lines):
    line = lines[i]
    if "    // 2. Videos (Reactively updates when video category changes)" in line:
        # Skip until the end of getCuratedPlaylistTitle
        while i < len(lines):
            if "    // Knowledge Hub Search" in lines[i]:
                new_lines.append(lines[i])
                break
            i += 1
    else:
        new_lines.append(line)
    i += 1

text = '\n'.join(new_lines)
with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)

