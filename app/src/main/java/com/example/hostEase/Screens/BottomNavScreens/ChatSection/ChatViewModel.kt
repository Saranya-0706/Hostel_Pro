package com.example.hostEase.Screens.BottomNavScreens.ChatSection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages = _messages.asStateFlow()


    fun loadMessages(chatRoomId : String){
        viewModelScope.launch {
            ChatRepository.getMessages(chatRoomId).collect(){
                _messages.value = it
            }
        }
    }

    fun sendMessage(chatRoomId: String,message: ChatMessage){
        ChatRepository.sendMessage(chatRoomId,message)
    }



}