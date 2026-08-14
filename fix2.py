with open('app/src/main/java/com/example/MainActivity.kt', 'r') as f:
    lines = f.readlines()

new_lines = []
skip = False
for i, line in enumerate(lines):
    if "override fun onDestroy()" in line:
        if "override fun onDestroy()" in lines[i+5] or "override fun onDestroy()" in lines[i-5] or skip:
            pass # we'll just handle it this way: keep track of how many we saw
            
count = 0
for line in lines:
    if "override fun onDestroy()" in line:
        count += 1
        if count > 1:
            skip = True
            
new_lines = []
in_destroy = False
count = 0
for line in lines:
    if "override fun onDestroy()" in line:
        count += 1
        if count > 1:
            in_destroy = True
    
    if not in_destroy:
        new_lines.append(line)
        
    if in_destroy and "}" in line:
        in_destroy = False

with open('app/src/main/java/com/example/MainActivity.kt', 'w') as f:
    f.writelines(new_lines)
