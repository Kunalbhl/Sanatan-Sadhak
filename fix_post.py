with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    text = f.read()

target = """                                            if (canDelete) {
                                                Spacer(modifier = Modifier.width(8.dp))
                                                IconButton(
                                                    onClick = { viewModel.deletePost(post.id) },
                                                    modifier = Modifier.size(24.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Delete,
                                                        contentDescription = "Delete",
                                                        tint = Color.Red.copy(alpha=0.7f),
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }"""

replacement = """                                            if (canDelete) {
                                                Spacer(modifier = Modifier.width(8.dp))
                                                IconButton(
                                                    onClick = { viewModel.deletePost(post.id) },
                                                    modifier = Modifier.size(24.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Delete,
                                                        contentDescription = "Delete",
                                                        tint = Color.Red.copy(alpha=0.7f),
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }
                                            if (isAdmin) {
                                                Spacer(modifier = Modifier.width(8.dp))
                                                IconButton(
                                                    onClick = { viewModel.togglePinPost(post) },
                                                    modifier = Modifier.size(24.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = if (post.isPinned) Icons.Default.Verified else Icons.Default.VerifiedUser,
                                                        contentDescription = "Pin/Unpin",
                                                        tint = if (post.isPinned) MaterialTheme.colorScheme.primary else Color.Gray,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }"""

text = text.replace(target, replacement)
with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(text)
