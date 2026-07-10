import re
with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    text = f.read()

funcs = """
    fun upvoteComment(comment: com.example.data.PostComment) {
        viewModelScope.launch {
            val email = userEmail.value
            if (email.isEmpty()) return@launch
            var upEmails = comment.upvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            var downEmails = comment.downvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            if (upEmails.contains(email)) upEmails.remove(email)
            else { upEmails.add(email); downEmails.remove(email) }
            repository.updateComment(comment.copy(upvotes = upEmails.size, downvotes = downEmails.size, upvotedByEmails = upEmails.joinToString(","), downvotedByEmails = downEmails.joinToString(",")))
        }
    }

    fun downvoteComment(comment: com.example.data.PostComment) {
        viewModelScope.launch {
            val email = userEmail.value
            if (email.isEmpty()) return@launch
            var upEmails = comment.upvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            var downEmails = comment.downvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            if (downEmails.contains(email)) downEmails.remove(email)
            else { downEmails.add(email); upEmails.remove(email) }
            repository.updateComment(comment.copy(upvotes = upEmails.size, downvotes = downEmails.size, upvotedByEmails = upEmails.joinToString(","), downvotedByEmails = downEmails.joinToString(",")))
        }
    }

    fun deleteComment(id: Int) {
        viewModelScope.launch {
            repository.deleteComment(id)
        }
    }
}
"""

if text.endswith("}\n"):
    text = text[:-2] + funcs
elif text.endswith("}"):
    text = text[:-1] + funcs

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(text)
