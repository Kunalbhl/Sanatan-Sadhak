import sys

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    text = f.read()

target = """                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.clickable { viewModel.selectPost(post.id) }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Comment,"""

replacement = """                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            if (isAdmin) {
                                                IconButton(
                                                    onClick = { viewModel.togglePinPost(post) },
                                                    modifier = Modifier.size(36.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = androidx.compose.material.icons.Icons.Default.PushPin,
                                                        contentDescription = "Pin",
                                                        tint = if (post.isPinned) MaterialTheme.colorScheme.primary else Color.Gray,
                                                        modifier = Modifier.size(18.dp)
                                                    )
                                                }
                                            }
                                            val userName by viewModel.userName.collectAsState()
                                            if (isAdmin || post.author == userName) {
                                                IconButton(
                                                    onClick = { viewModel.deletePost(post) },
                                                    modifier = Modifier.size(36.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = androidx.compose.material.icons.Icons.Default.Delete,
                                                        contentDescription = "Delete",
                                                        tint = Color.Red.copy(alpha=0.7f),
                                                        modifier = Modifier.size(18.dp)
                                                    )
                                                }
                                            }
                                        }
                                        
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.clickable { viewModel.selectPost(post.id) }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Comment,"""

if target in text:
    text = text.replace(target, replacement)
    with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
        f.write(text)
    print("Added Pin and Delete icons")
else:
    print("Target not found")
