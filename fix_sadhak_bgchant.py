import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    lines = f.readlines()

new_lines = []
skip = False
for line in lines:
    if "fun toggleBgChant()" in line:
        new_lines.append(line)
        new_lines.append("        com.example.data.AppPreferences.toggleBgChant()\n")
        skip = True
    elif "fun setBgChantVolume(" in line:
        new_lines.append(line)
        new_lines.append("        com.example.data.AppPreferences.setBgChantVolume(vol)\n")
        skip = True
    elif skip and "}" in line:
        new_lines.append(line)
        skip = False
    elif not skip:
        if "_bgChantEnabled.value =" in line or "_bgChantVolume.value =" in line:
            pass
        elif "val bgChantVolume: StateFlow<Float> = _bgChantVolume" in line:
            # I think the python script messed up this line. Let's make sure it's correct.
            pass
        else:
            new_lines.append(line)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.writelines(new_lines)
