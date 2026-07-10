package com.example.data

import android.util.Log
import android.util.Xml
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParser
import java.io.StringReader

object YoutubeFetcher {
    private val client = OkHttpClient()

    fun fetchVideosFromChannel(apiKey: String, channelId: String = "UCzHzPEdLJEvTSHn_ev3JYBA"): List<BhaktiVideo> {
        val list = mutableListOf<BhaktiVideo>()
        var success = false

        // 1. Try YouTube Data API v3 if API Key is available
        val hasRealApiKey = apiKey.isNotEmpty() && 
                            apiKey != "MY_GEMINI_API_KEY" && 
                            apiKey != "YOUR_YOUTUBE_API_KEY" && 
                            !apiKey.contains("PLACEHOLDER")

        if (hasRealApiKey) {
            try {
                val url = "https://www.googleapis.com/youtube/v3/search?key=$apiKey&channelId=$channelId&part=snippet&order=date&maxResults=50&type=video"
                val request = Request.Builder().url(url).build()
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val body = response.body?.string()
                        if (body != null) {
                            val json = JSONObject(body)
                            val items = json.optJSONArray("items")
                            if (items != null) {
                                for (i in 0 until items.length()) {
                                    val item = items.getJSONObject(i)
                                    val idObj = item.optJSONObject("id")
                                    val videoId = idObj?.optString("videoId", "") ?: ""
                                    if (videoId.isNotEmpty()) {
                                        val snippet = item.optJSONObject("snippet")
                                        val title = snippet?.optString("title", "") ?: ""
                                        val description = snippet?.optString("description", "") ?: ""
                                        val publishDate = snippet?.optString("publishedAt", "") ?: ""
                                        
                                        // Filter out gaming content
                                        val combinedText = "$title $description".lowercase()
                                        if (combinedText.contains("gaming") || 
                                            combinedText.contains("gameplay") || 
                                            combinedText.contains("kaal gaming")
                                        ) {
                                            continue
                                        }

                                        val thumbnails = snippet?.optJSONObject("thumbnails")
                                        val mediumThumb = thumbnails?.optJSONObject("medium")
                                        val highThumb = thumbnails?.optJSONObject("high")
                                        val defaultThumb = thumbnails?.optJSONObject("default")
                                        val thumbnail = highThumb?.optString("url") 
                                            ?: mediumThumb?.optString("url") 
                                            ?: defaultThumb?.optString("url") 
                                            ?: ""

                                        val category = determineCategory(title, description)
                                        list.add(
                                            BhaktiVideo(
                                                title = title,
                                                description = if (description.length > 250) description.take(250) + "..." else description,
                                                category = category,
                                                videoId = videoId,
                                                isCustom = false,
                                                thumbnail = thumbnail,
                                                publishDate = publishDate
                                            )
                                        )
                                    }
                                }
                                success = true
                                Log.i("YoutubeFetcher", "Successfully synced ${list.size} videos from YouTube Data API v3.")
                            }
                        }
                    } else {
                        Log.e("YoutubeFetcher", "YouTube Data API error: code ${response.code}, message: ${response.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e("YoutubeFetcher", "Error fetching from YouTube Data API v3, will fall back to RSS", e)
            }
        }

        // 2. Fallback to XML RSS Feed if YouTube API failed or key is empty
        if (!success) {
            try {
                val url = "https://www.youtube.com/feeds/videos.xml?channel_id=$channelId"
                val request = Request.Builder().url(url).build()
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val body = response.body?.string()
                        if (body != null) {
                            return parseFeedXml(body)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("YoutubeFetcher", "Error fetching RSS backup", e)
            }
        }

        return list
    }

    private fun parseFeedXml(xmlContent: String): List<BhaktiVideo> {
        val videos = mutableListOf<BhaktiVideo>()
        try {
            val factory = Xml.newPullParser()
            factory.setInput(StringReader(xmlContent))
            var eventType = factory.eventType
            
            var currentVideoId = ""
            var currentTitle = ""
            var currentDescription = ""
            var currentThumbnail = ""
            var currentPublishDate = ""
            var insideEntry = false

            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = factory.name
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (tagName == "entry") {
                            insideEntry = true
                            currentVideoId = ""
                            currentTitle = ""
                            currentDescription = ""
                            currentThumbnail = ""
                            currentPublishDate = ""
                        } else if (insideEntry) {
                            when (tagName) {
                                "yt:videoId" -> {
                                    currentVideoId = factory.nextText()
                                }
                                "title" -> {
                                    currentTitle = factory.nextText()
                                }
                                "media:description" -> {
                                    currentDescription = factory.nextText()
                                }
                                "summary" -> {
                                    currentDescription = factory.nextText()
                                }
                                "published" -> {
                                    currentPublishDate = factory.nextText()
                                }
                                "media:thumbnail" -> {
                                    currentThumbnail = factory.getAttributeValue(null, "url") ?: ""
                                }
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (tagName == "entry") {
                            insideEntry = false
                            if (currentVideoId.isNotEmpty() && currentTitle.isNotEmpty()) {
                                // Filter out gaming content
                                val combinedText = "$currentTitle $currentDescription".lowercase()
                                if (!combinedText.contains("gaming") && 
                                    !combinedText.contains("gameplay") && 
                                    !combinedText.contains("kaal gaming")
                                ) {
                                    val category = determineCategory(currentTitle, currentDescription)
                                    videos.add(
                                        BhaktiVideo(
                                            title = currentTitle,
                                            description = if (currentDescription.length > 250) currentDescription.take(250) + "..." else currentDescription,
                                            category = category,
                                            videoId = currentVideoId,
                                            isCustom = false,
                                            thumbnail = currentThumbnail.ifEmpty { "https://img.youtube.com/vi/$currentVideoId/hqdefault.jpg" },
                                            publishDate = currentPublishDate
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                eventType = factory.next()
            }
        } catch (e: Exception) {
            Log.e("YoutubeFetcher", "XML Parsing error", e)
        }
        return videos
    }

    fun determineCategory(title: String, description: String): String {
        val combined = "$title $description".lowercase()
        return when {
            combined.contains("aarti") || combined.contains("abhishek") || combined.contains("आरती") || combined.contains("अभिषेक") -> "Aarti & Abhishek"
            combined.contains("darshan") || combined.contains("puja") || combined.contains("pooja") || combined.contains("दर्शन") || combined.contains("पूजा") -> "Darshan & Puja"
            combined.contains("mandir") || combined.contains("temple") || combined.contains("मंदिर") -> "Temple Visits"
            else -> "Devotional Stories"
        }
    }
}
