import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    content = f.read()

# Fix articles
content = content.replace('''            )
        }
    // 2. Videos''', '''            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 2. Videos''')

# Fix videos
content = content.replace('''                "Stories" -> list.filter { it.category.contains("Stories", ignoreCase = true) || it.category.contains("Experiences", ignoreCase = true) }
                else -> list.filter { it.category.equals(cat, ignoreCase = true) }
            }
    // Curated dynamic playlist''', '''                "Stories" -> list.filter { it.category.contains("Stories", ignoreCase = true) || it.category.contains("Experiences", ignoreCase = true) }
                else -> list.filter { it.category.equals(cat, ignoreCase = true) }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Curated dynamic playlist''')

# Fix curatedPlaylistVideos
content = content.replace('''            allVideos.take(6)
        }
    fun getCuratedPlaylistTitle''', '''            allVideos.take(6)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getCuratedPlaylistTitle''')

# Wait, `CommonScreens.kt` has unresolved `guestQuestionCount`, `sankalpaLogs`, `addSankalpaLog`.
# Let's fix those!
