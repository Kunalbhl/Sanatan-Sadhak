package com.example.ui.screens

import androidx.compose.runtime.*
import com.example.ui.viewmodel.SadhakViewModel
import com.example.ui.viewmodel.VideoViewModel

@Composable
fun AdminScreen(
    viewModel: SadhakViewModel,
    videoViewModel: VideoViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = com.example.ui.viewmodel.AppViewModelProvider.Factory)
) {
    val isAdmin by viewModel.isAdmin.collectAsState()
    
    if (!isAdmin) {
        AccessDeniedScreen(viewModel = viewModel)
    } else {
        AdminControlCenterScreen(
            sadhakViewModel = viewModel,
            videoViewModel = videoViewModel
        )
    }
}
