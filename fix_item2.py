with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

bad = """        }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Knowledge Hub"""

good = """        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Knowledge Hub"""

content = content.replace(bad, good)

bad2 = """                    Text(text = if (isEng) "Community" else "सत्संग", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 13.sp)
                }
            }
        }
    }
}
// --- KNOWLEDGE HUB SCREEN ---"""

good2 = """                    Text(text = if (isEng) "Community" else "सत्संग", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 13.sp)
                }
            }
        }
    }
}
}
// --- KNOWLEDGE HUB SCREEN ---"""

content = content.replace(bad2, good2)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
