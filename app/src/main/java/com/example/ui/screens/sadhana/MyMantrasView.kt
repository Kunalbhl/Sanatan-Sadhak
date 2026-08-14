package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.viewmodel.SadhakViewModel
import com.example.data.FavoriteMantra

@Composable
fun MyMantrasView(viewModel: SadhakViewModel, isEng: Boolean, onMantraClick: (String) -> Unit) {
    val mantras by viewModel.favoriteMantras.collectAsState(initial = emptyList())
    
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        mantras.forEachIndexed { index, mantra ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = mantra.title, style = MaterialTheme.typography.titleMedium)
                    Row {
                        IconButton(onClick = { /* Move Up */ }) { Text("↑") }
                        IconButton(onClick = { /* Move Down */ }) { Text("↓") }
                        IconButton(onClick = { /* Set Timer */ }) { Text("⏰") }
                    }
                }
            }
        }
    }
}
