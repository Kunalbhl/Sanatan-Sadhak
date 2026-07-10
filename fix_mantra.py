with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "r") as f:
    text = f.read()

target = """                val favoriteMantras by viewModel.favoriteMantras.androidx.compose.runtime.collectAsState(initial = emptyList())
                val isBookmarked = favoriteMantras.any { it.title == prayerData.titleEn || it.title == prayerData.titleHi }
                IconButton(onClick = {
                    if (isBookmarked) {
                        val toDel = favoriteMantras.find { it.title == prayerData.titleEn || it.title == prayerData.titleHi }
                        if (toDel != null) {
                            viewModel.deleteFavoriteMantra(toDel.id)
                        }
                    } else {
                        viewModel.addFavoriteMantra(
                            title = if (isEnglish) prayerData.titleEn else prayerData.titleHi,
                            content = if (isEnglish) prayerData.sanskritLyrics.take(100) else prayerData.sanskritLyrics.take(100),
                            youtubeUrl = prayerData.youtubeId
                        )
                    }
                }) {
                    androidx.compose.material3.Icon(
                        imageVector = if (isBookmarked) androidx.compose.material.icons.Icons.Default.Bookmark else androidx.compose.material.icons.Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }"""

replacement = """                val favoriteMantras by viewModel.favoriteMantras.collectAsState(initial = emptyList())
                val isBookmarked = favoriteMantras.any { it.title == prayerData.titleEn || it.title == prayerData.titleHi }
                IconButton(onClick = {
                    if (isBookmarked) {
                        val toDel = favoriteMantras.find { it.title == prayerData.titleEn || it.title == prayerData.titleHi }
                        if (toDel != null) {
                            viewModel.deleteFavoriteMantra(toDel.id)
                        }
                    } else {
                        viewModel.addFavoriteMantra(
                            title = if (isEnglish) prayerData.titleEn else prayerData.titleHi,
                            content = prayerData.sanskritOrHindi.take(100),
                            youtubeUrl = prayerData.youtubeUrl
                        )
                    }
                }) {
                    androidx.compose.material3.Icon(
                        imageVector = if (isBookmarked) androidx.compose.material.icons.Icons.Filled.Favorite else androidx.compose.material.icons.Icons.Default.FavoriteBorder,
                        contentDescription = "Bookmark",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }"""

text = text.replace(target, replacement)
text = text.replace("import androidx.compose.runtime.collectAsState", "")
text = "import androidx.compose.runtime.collectAsState\n" + text
text = text.replace("import androidx.compose.material.icons.filled.Bookmark", "")
text = text.replace("import androidx.compose.material.icons.filled.BookmarkBorder", "")
text = "import androidx.compose.material.icons.filled.Favorite\nimport androidx.compose.material.icons.filled.FavoriteBorder\n" + text
with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "w") as f:
    f.write(text)
