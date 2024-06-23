package com.example.hostEase.Screens.BottomNavScreens.Announcements

import java.util.concurrent.TimeUnit

fun getTimeAgo(timeStamp : Long,currentTime : Long): String{

    val diffMillis = currentTime - timeStamp

    val seconds = TimeUnit.MILLISECONDS.toSeconds(diffMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(diffMillis)
    val days = TimeUnit.MILLISECONDS.toDays(diffMillis)

    return when{
        days > 0 -> if(days == 1L)  "$days day ago" else "$days days ago"
        hours > 0 -> if(hours == 1L)  "$hours hour ago" else "$hours hours ago"
        minutes> 0 -> if(minutes == 1L)  "$minutes minute ago" else "$minutes minutes ago"
        else  -> "$seconds seconds ago"
    }
}