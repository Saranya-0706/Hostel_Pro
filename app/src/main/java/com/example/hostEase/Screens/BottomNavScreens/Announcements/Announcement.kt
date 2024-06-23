package com.example.hostEase.Screens.BottomNavScreens.Announcements

data class Announcement(
    val id:String = "",
    val heading : String = "",
    val content : String = "",
    val attachmentUrlHeader: String? = "",
    val attachmentUrl : String? = null,
    val timeStamp : Long = System.currentTimeMillis()
)
