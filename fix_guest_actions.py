import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

# Add Karma
karma_add = """Button(
                                onClick = {
                                    if (newDeed.isNotBlank()) {
                                        if (viewModel.registerGuestAction()) {
                                            viewModel.logKarmaDeed(newDeed)
                                            newDeed = ""
                                        }
                                    }
                                }"""
content = re.sub(r'Button\(\s*onClick = \{\s*if \(newDeed\.isNotBlank\(\)\) \{\s*viewModel\.logKarmaDeed\(newDeed\)\s*newDeed = ""\s*\}\s*\}', karma_add, content)

# Add Gratitude
gratitude_add = """Button(
                                onClick = {
                                    if (newGratitude.isNotBlank()) {
                                        if (viewModel.registerGuestAction()) {
                                            viewModel.logGratitude(newGratitude)
                                            newGratitude = ""
                                        }
                                    }
                                }"""
content = re.sub(r'Button\(\s*onClick = \{\s*if \(newGratitude\.isNotBlank\(\)\) \{\s*viewModel\.logGratitude\(newGratitude\)\s*newGratitude = ""\s*\}\s*\}', gratitude_add, content)

# Add Community Post
post_add = """Button(
                            onClick = {
                                if (newTitle.isNotBlank() && newContent.isNotBlank()) {
                                    if (viewModel.registerGuestAction()) {
                                        viewModel.addPost(newTitle, newContent)
                                        isAddingPost = false
                                        newTitle = ""
                                        newContent = ""
                                    }
                                }
                            }"""
content = re.sub(r'Button\(\s*onClick = \{\s*if \(newTitle\.isNotBlank\(\) && newContent\.isNotBlank\(\)\) \{\s*viewModel\.addPost\(newTitle, newContent\)\s*isAddingPost = false\s*newTitle = ""\s*newContent = ""\s*\}\s*\}', post_add, content)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
