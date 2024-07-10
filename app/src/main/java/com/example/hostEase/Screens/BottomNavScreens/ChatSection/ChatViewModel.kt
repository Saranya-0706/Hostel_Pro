package com.example.hostEase.Screens.BottomNavScreens.ChatSection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages = _messages.asStateFlow()

    private val _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val currentUser = _currentUser.asStateFlow()


    fun loadMessages(hostel : String, userRole : String){
        viewModelScope.launch {
            ChatRepository.getMessages(hostel, userRole).collect(){
                _messages.value = it
            }
        }
    }

    fun sendMessage(hostel: String, userRole: String,message: ChatMessage){
        ChatRepository.sendMessage(hostel,userRole,message)
    }

    fun deleteMessage(userRole: String, hostel: String, messageId : String){
        ChatRepository.deleteMessage(userRole, hostel, messageId)
    }

}