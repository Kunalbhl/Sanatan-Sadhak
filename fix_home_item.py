with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

bad_str = """            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {"""

good_str = """        item {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {"""

content = content.replace(bad_str, good_str)

bad_str2 = """                    Text(text = if (isEng) "Community" else "सत्संग", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 13.sp)
                }
            }
        }
    }
}
// --- KNOWLEDGE HUB SCREEN ---"""

good_str2 = """                    Text(text = if (isEng) "Community" else "सत्संग", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 13.sp)
                }
            }
        }
        }
    }
}
// --- KNOWLEDGE HUB SCREEN ---"""

content = content.replace(bad_str2, good_str2)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
