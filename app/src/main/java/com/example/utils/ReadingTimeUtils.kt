package com.example.utils

object ReadingTimeUtils {
    // Average reading speed is 200 words per minute.
    private const val WORDS_PER_MINUTE = 200

    fun calculateReadTime(text: String): Int {
        val wordCount = text.split(Regex("\\s+")).filter { it.isNotEmpty() }.size
        return (wordCount / WORDS_PER_MINUTE).coerceAtLeast(1)
    }
}
