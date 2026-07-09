package com.example.data

import android.util.Log
import android.util.Xml
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParser
import java.io.StringReader

object YoutubeFetcher {
    private val client = OkHttpClient()

    fun fetchVideosFromChannel(channelId: String = "UCzHzPEdLJEvTSHn_ev3JYBA"): List<BhaktiVideo> {
        val url = "https://www.youtube.com/feeds/videos.xml?channel_id=$channelId"
        val request = Request.Builder().url(url).build()
        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    Log.e("YoutubeFetcher", "Failed to fetch channel feed: ${response.code}")
                    return emptyList()
                }
                val body = response.body?.string() ?: return emptyList()
                return parseFeedXml(body)
            }
        } catch (e: Exception) {
            Log.e("YoutubeFetcher", "Error fetching YouTube videos", e)
            return emptyList()
        }
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
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (tagName == "entry") {
                            insideEntry = false
                            if (currentVideoId.isNotEmpty() && currentTitle.isNotEmpty()) {
                                // Determine Category: Darshan, Puja, or Stories
                                val category = determineCategory(currentTitle, currentDescription)
                                videos.add(
                                    BhaktiVideo(
                                        title = currentTitle,
                                        description = if (currentDescription.length > 150) currentDescription.take(150) + "..." else currentDescription,
                                        category = category,
                                        videoId = currentVideoId,
                                        isCustom = false
                                    )
                                )
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

    private fun determineCategory(title: String, description: String): String {
        val combined = "$title $description".lowercase()
        return when {
            combined.contains("darshan") || combined.contains("दर्शन") || combined.contains("shringar") || combined.contains("श्रृंगार") -> "Darshan"
            combined.contains("puja") || combined.contains("पूजा") || combined.contains("aarti") || combined.contains("आरती") || combined.contains("abhishek") || combined.contains("अभिषेक") || combined.contains("bhajan") || combined.contains("भजन") -> "Puja"
            else -> "Stories"
        }
    }
}
