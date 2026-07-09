import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

bad1 = """                OutlinedTextField(
                    value = overrideThoughtEn,
                    onValueChange = { overrideThoughtEn = it },
                    label = { Text("Custom Thought (English)") },
                    modifier = Modifier.fillMaxWidth()
                )"""

good1 = """                OutlinedTextField(
                    value = overrideThoughtEn,
                    onValueChange = { overrideThoughtEn = it },
                    label = { Text("Custom Thought (English)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                )"""

bad2 = """                OutlinedTextField(
                    value = overrideThoughtHi,
                    onValueChange = { overrideThoughtHi = it },
                    label = { Text("Custom Thought (Hindi)") },
                    modifier = Modifier.fillMaxWidth()
                )"""

good2 = """                OutlinedTextField(
                    value = overrideThoughtHi,
                    onValueChange = { overrideThoughtHi = it },
                    label = { Text("Custom Thought (Hindi)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                )"""

content = content.replace(bad1, good1).replace(bad2, good2)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
