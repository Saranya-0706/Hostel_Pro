package com.example.hostEase.Navigation_App.navigation

import com.example.hostEase.R

sealed class NavDrawerItems(
    val route:String,
    val icon: Int,
    val name:String
){
    data object Profile : NavDrawerItems("profile", R.drawable.profile_user,"Profile")
    data object Settings: NavDrawerItems("Settings", R.drawable.settings,"Settings")
    data object b : NavDrawerItems("b", R.drawable.complaint,"b")
    data object c : NavDrawerItems("c", R.drawable.chat,"c")
}