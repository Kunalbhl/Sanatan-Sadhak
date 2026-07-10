with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    text = f.read()

# Let's remove the stray bracket and make sure we close the `else` block properly.
target = """                    if (panchangLastUpdated.isNotEmpty()) {
                        Text(
                            text = "Last updated: $panchangLastUpdated",
                            fontSize = 10.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                            textAlign = TextAlign.End
                        )
                    }

                }
            }
        }
    }
            }

        // Category Options Tile Grid (Sacred Services)"""

replacement = """                    if (panchangLastUpdated.isNotEmpty()) {
                        Text(
                            text = "Last updated: $panchangLastUpdated",
                            fontSize = 10.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                            textAlign = TextAlign.End
                        )
                    }

                }
            }
        }
    } // closes else
} // closes item

        // Category Options Tile Grid (Sacred Services)"""

text = text.replace(target, replacement)
with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(text)
