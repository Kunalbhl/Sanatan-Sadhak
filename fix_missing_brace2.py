import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

text = text.replace(
"""                }
            )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    // 2. Videos""",
"""                }
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<KnowledgeArticle>())
    // 2. Videos""")


text = text.replace(
"""                "Stories" -> list.filter { it.category.contains("Stories", ignoreCase = true) || it.category.contains("Experiences", ignoreCase = true) }
                else -> list.filter { it.category.equals(cat, ignoreCase = true) }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    // Curated dynamic playlist""",
"""                "Stories" -> list.filter { it.category.contains("Stories", ignoreCase = true) || it.category.contains("Experiences", ignoreCase = true) }
                else -> list.filter { it.category.equals(cat, ignoreCase = true) }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<BhaktiVideo>())
    // Curated dynamic playlist""")

# Add two trailing braces
text += '}\n}\n'

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)

