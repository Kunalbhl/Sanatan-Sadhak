import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

# Fix Dhyana Sadhana text in Hindi
content = content.replace('"ध्यान टाइमर"', '"ध्यान साधना"')
content = content.replace('"कर्म ट्रैकर"', '"कर्म चक्र"')

# Add Community and Knowledge Hub shortcuts
additional_shortcuts = """
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Knowledge Hub
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha=0.3f), RoundedCornerShape(16.dp))
                        .clickable { viewModel.setScreen("Knowledge") }
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.MenuBook, contentDescription = "Knowledge", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = if (isEng) "Knowledge Hub" else "ज्ञान सागर", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 13.sp)
                }
                
                // Community
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha=0.3f), RoundedCornerShape(16.dp))
                        .clickable { viewModel.setScreen("Community") }
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.Forum, contentDescription = "Community", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = if (isEng) "Community" else "सत्संग", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 13.sp)
                }
            }
        }
"""

content = content.replace('} // End of Gratitude Column\n            }\n        }\n    }\n}', 
                          '} // End of Gratitude Column\n            }' + additional_shortcuts + '\n    }\n}')

# Since I don't know the exact string to replace, I will use regex to find the end of HomeScreen
# The HomeScreen ends with a } followed by KnowledgeScreen
pattern = r'(\s*\}\s*\}\s*)(// --- KNOWLEDGE HUB SCREEN ---)'
match = re.search(pattern, content)
if match:
    content = content[:match.start(1)] + additional_shortcuts + "\n    }\n}\n" + match.group(2) + content[match.end(2):]

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
