package com.example.hostEase.Navigation_App.navigation

import com.example.hostEase.R

sealed class BottomNavItems(
    val route:String,
    val icon: Int,
    val name:String
){
    data object General : BottomNavItems("home", R.drawable.announce,"General")
    data object Complaints : BottomNavItems("complaint", R.drawable.complaint,"Complaint")
    data object LostFound : BottomNavItems("lostFound", R.drawable.lost_found,"LostFound")
    data object Chat : BottomNavItems("chat", R.drawable.chat,"Chat")

}