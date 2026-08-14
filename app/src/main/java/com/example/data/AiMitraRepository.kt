package com.example.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class AiMitraRepository(context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val chatDao = db.chatMessageDao()
    val geminiService = GeminiService

    fun getChatMessages(): Flow<List<ChatMessage>> = chatDao.getAllMessages()
    suspend fun addChatMessage(msg: ChatMessage) = chatDao.insertMessage(msg)
    suspend fun clearChat() = chatDao.clearChat()
}
