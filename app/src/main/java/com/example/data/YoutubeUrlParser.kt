package com.example.data

import java.util.regex.Pattern

object YoutubeUrlParser {

    private val YOUTUBE_VIDEO_ID_REGEX = Pattern.compile(
        "^.*(?:(?:youtu\\.be\\/|v\\/|vi\\/|u\\/\\w\\/|embed\\/|shorts\\/)|(?:(?:watch)?\\?v(?:i)?=|\\&v(?:i)?=))([^#\\&\\?]*).*"
    )

    private val STANDALONE_VIDEO_ID_REGEX = Pattern.compile("^[a-zA-Z0-9_-]{11}$")

    /**
     * Extracts the 11-character YouTube video ID from a URL or raw ID string.
     * Returns null if the input is not a valid YouTube URL or ID.
     */
    fun extractVideoId(input: String): String? {
        val trimmed = input.trim()
        if (trimmed.isEmpty()) return null

        // Check if user entered raw 11-char ID directly
        if (STANDALONE_VIDEO_ID_REGEX.matcher(trimmed).matches()) {
            return trimmed
        }

        val matcher = YOUTUBE_VIDEO_ID_REGEX.matcher(trimmed)
        if (matcher.matches()) {
            val group = matcher.group(1)
            if (group != null && group.length == 11) {
                return group
            }
        }
        return null
    }

    /**
     * Generates standard high quality thumbnail URL for a given video ID.
     */
    fun getThumbnailUrl(videoId: String): String {
        return "https://img.youtube.com/vi/$videoId/hqdefault.jpg"
    }

    /**
     * Generates clean YouTube watch URL.
     */
    fun getWatchUrl(videoId: String): String {
        return "https://www.youtube.com/watch?v=$videoId"
    }

    /**
     * Validates whether a category is one of the supported devotional categories.
     */
    val SUPPORTED_CATEGORIES = listOf(
        "Sanatan Sadhak",
        "Mantra",
        "Bhajan",
        "Pravachan",
        "Pooja Vidhi",
        "Granth / Shastra",
        "Meditation",
        "Panchang",
        "Festival",
        "Spiritual Knowledge",
        "Darshan & Puja",
        "Aarti & Abhishek",
        "Temple Visits",
        "Devotional Stories",
        "Motivation",
        "Other"
    )
}
