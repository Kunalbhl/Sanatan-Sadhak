package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.data.ChatMessage
import com.example.ui.components.SacredCard
import com.example.ui.viewmodel.SadhakViewModel

@Composable
fun AccessDeniedScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = Icons.Default.Warning, contentDescription = "Denied", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(64.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = if (isEng) "Access Denied" else "पहुंच अस्वीकृत", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 20.sp)
        Text(text = if (isEng) "You do not have permission to view this page." else "आपको इस पृष्ठ को देखने की अनुमति नहीं है।", color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
fun AdminScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    val isPublicPosting by viewModel.isPublicPostingEnabled.collectAsState()
    var isPublic by remember { mutableStateOf(isPublicPosting) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Admin Control Panel", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
        Spacer(modifier = Modifier.height(16.dp))
        
        SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Global Settings", fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Enable Public Posting")
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(checked = isPublic, onCheckedChange = { 
                        isPublic = it
                        viewModel.setPublicPosting(it)
                    })
                }
            }
        }
    }
}

@Composable
fun ChatScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    val messages by viewModel.chatMessages.collectAsState()
    val isLoading by viewModel.isChatLoading.collectAsState()
    var currentInput by remember { mutableStateOf("") }
    val userRole by viewModel.userRole.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(if (isEng) "Sadhak Mitra AI" else "साधक मित्र AI", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
        
        // Greeting and Recommendations
        Text(
            text = if (isEng) "Pranam Devotee! May your day be blessed with peace and spiritual growth." else "प्रणाम भक्त! आपका दिन शांति और आध्यात्मिक विकास से भरा हो।",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 16.dp)
        )
        
        Text(if (isEng) "Explore Sanatan Dharma:" else "सनातन धर्म के मार्ग:", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 8.dp))
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(selected = false, onClick = { viewModel.setScreen("Bhakti") }, label = { Text("Mantra") })
            FilterChip(selected = false, onClick = { viewModel.setScreen("Knowledge") }, label = { Text("Articles") })
            FilterChip(selected = false, onClick = { viewModel.setScreen("Community") }, label = { Text("Community") })
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        val guestQuestionCount by viewModel.guestQuestionCount.collectAsState()

        if (userRole == "Guest") {
             Text(
                 text = if (isEng) "Questions remaining: ${10 - guestQuestionCount}" else "शेष प्रश्न: ${10 - guestQuestionCount}",
                 style = MaterialTheme.typography.bodySmall,
                 color = MaterialTheme.colorScheme.primary,
                 modifier = Modifier.padding(bottom = 8.dp)
             )
        }
        
        // Always show the chat interface
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(messages) { msg ->
                val isUser = msg.sender == "user"
                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart) {
                    Box(modifier = Modifier.background(if (isUser) MaterialTheme.colorScheme.primary.copy(alpha=0.1f) else MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp)).padding(12.dp)) {
                        Text(msg.content, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = currentInput,
                onValueChange = { currentInput = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Ask Sadhak Mitra...") }
            )
            IconButton(onClick = {
                if (currentInput.isNotBlank()) {
                    viewModel.sendMessage(currentInput)
                    currentInput = ""
                }
            }) {
                Icon(Icons.Default.Send, contentDescription = "Send", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
