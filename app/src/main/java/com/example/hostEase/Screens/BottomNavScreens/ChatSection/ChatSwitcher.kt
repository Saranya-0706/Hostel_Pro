package com.example.hostEase.Screens.BottomNavScreens.ChatSection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hostEase.Screens.NavDrawerScreens.Profile.UserViewModel

@Composable
fun ChatSwitcher(chatViewModel: ChatViewModel = viewModel(),userViewModel: UserViewModel = viewModel()) {

    val user by userViewModel.user.observeAsState()
    val currentUser by chatViewModel.currentUser.collectAsStateWithLifecycle()
    var userRole by remember { mutableStateOf("") }
    var userHostel by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        userViewModel.loadUserProfile()
    }
    userRole = user?.role ?: "Student"
    userHostel = user?.hostel ?: ""

    Box(modifier = Modifier.fillMaxSize()){
        ChatScreen(hostel = userHostel, userRole = userRole)
    }
}