with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    content = f.read()

# Fix articles
old1 = """            )
        }
    // 2. Videos"""
new1 = """            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    // 2. Videos"""
content = content.replace(old1, new1)

# Fix videos
old2 = """                else -> list.filter { it.category.equals(cat, ignoreCase = true) }
            }
    // Curated dynamic playlist"""
new2 = """                else -> list.filter { it.category.equals(cat, ignoreCase = true) }
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    // Curated dynamic playlist"""
content = content.replace(old2, new2)

# Fix curatedPlaylistVideos
old3 = """            allVideos.take(6)
        }
    fun getCuratedPlaylistTitle"""
new3 = """            allVideos.take(6)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun getCuratedPlaylistTitle"""
content = content.replace(old3, new3)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(content)
