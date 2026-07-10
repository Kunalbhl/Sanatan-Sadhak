import sys

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    text = f.read()

# For Video:
video_target = """                                IconButton(onClick = {
                                    val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(video.youtubeUrl))
                                    context.startActivity(intent)
                                }) {
                                    Icon(imageVector = Icons.Default.PlayCircle, contentDescription = "Play", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(40.dp))
                                }"""

video_replacement = """                                Row {
                                    IconButton(onClick = {
                                        val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                        val clip = android.content.ClipData.newPlainText("Video URL", video.youtubeUrl)
                                        clipboard.setPrimaryClip(clip)
                                        android.widget.Toast.makeText(context, "Link copied!", android.widget.Toast.LENGTH_SHORT).show()
                                    }) {
                                        Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(24.dp))
                                    }
                                    IconButton(onClick = {
                                        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(video.youtubeUrl))
                                        context.startActivity(intent)
                                    }) {
                                        Icon(imageVector = Icons.Default.PlayCircle, contentDescription = "Play", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(40.dp))
                                    }
                                }"""

if video_target in text:
    text = text.replace(video_target, video_replacement)
    print("Video Share added")

# For Article:
article_target = """                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = "Read",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )"""

article_replacement = """                                val context = androidx.compose.ui.platform.LocalContext.current
                                IconButton(onClick = {
                                    val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                    val clip = android.content.ClipData.newPlainText("Article", article.title + " - Read more in Sadhak Mitra App!")
                                    clipboard.setPrimaryClip(clip)
                                    android.widget.Toast.makeText(context, "Article shared to clipboard!", android.widget.Toast.LENGTH_SHORT).show()
                                }, modifier = Modifier.size(24.dp).padding(end=4.dp)) {
                                    Icon(Icons.Default.Share, contentDescription = "Share", tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(16.dp))
                                }
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = "Read",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )"""

if article_target in text:
    text = text.replace(article_target, article_replacement)
    print("Article Share added")

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(text)
