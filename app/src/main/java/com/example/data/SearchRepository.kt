package com.example.data

import android.content.Context

class SearchRepository(context: Context) {
    val knowledgeRepository = KnowledgeRepository(context)
    val videoRepository = VideoRepository(context)
    val mantraRepository = MantraRepository(context)
    val templeRepository = TempleRepository(context)
}
