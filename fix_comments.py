with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    text = f.read()

target = """                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = comment.content,
                                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
                            )
                        }
                    }"""

replacement = """                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = comment.content,
                                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = { viewModel.upvoteComment(comment) }, modifier = Modifier.size(24.dp)) {
                                        Icon(Icons.Default.ThumbUp, contentDescription = "Upvote", modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.primary)
                                    }
                                    Text(text = comment.upvotes.toString(), fontSize = 12.sp, modifier = Modifier.padding(horizontal = 4.dp))
                                    IconButton(onClick = { viewModel.downvoteComment(comment) }, modifier = Modifier.size(24.dp)) {
                                        Icon(Icons.Default.ThumbDown, contentDescription = "Downvote", modifier = Modifier.size(14.dp), tint = Color.Gray)
                                    }
                                    Text(text = comment.downvotes.toString(), fontSize = 12.sp, modifier = Modifier.padding(horizontal = 4.dp))
                                }
                                val userName by viewModel.userName.collectAsState()
                                val isAdmin by viewModel.isAdmin.collectAsState()
                                if (isAdmin || comment.author == userName) {
                                    IconButton(onClick = { viewModel.deleteComment(comment.id) }, modifier = Modifier.size(24.dp)) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(14.dp), tint = Color.Red.copy(alpha=0.7f))
                                    }
                                }
                            }
                        }
                    }"""

text = text.replace(target, replacement)
with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(text)
