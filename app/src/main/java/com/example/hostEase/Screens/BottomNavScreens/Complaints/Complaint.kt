package com.example.hostEase.Screens.BottomNavScreens.Complaints

data class Complaint(
    val type : String = "",
    val id:String = "",
    val heading : String = "",
    val content : String = "",
    val timeStamp : Long = System.currentTimeMillis(),
    val userName :String = "",
    val email :String = "",
    val userId :String = "",
    val hostel : String = "",
    var upVotes :Int = 0,
    var downVotes :Int = 0,
    var voters : Map<String, Boolean> = emptyMap()//maps userid to true for upvote and false for downvote
)
