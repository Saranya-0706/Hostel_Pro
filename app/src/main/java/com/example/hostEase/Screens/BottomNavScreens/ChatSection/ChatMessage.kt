package com.example.hostEase.Screens.BottomNavScreens.ChatSection

data class ChatMessage(
    val id : String = "",
    val senderId : String = "",
    val senderEmail : String = "",
    val senderName : String = "",
    val senderRole : String = "",
    val hostel : String = "",
    val message : String = "",
    val timeStamp : Long = 0L
)
