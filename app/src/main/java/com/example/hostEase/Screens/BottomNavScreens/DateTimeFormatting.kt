package com.example.hostEase.Screens.BottomNavScreens

import com.example.hostEase.Screens.BottomNavScreens.ChatSection.ChatMessage
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun getFormattedDate(timeStamp : Long ): String {

    val localTimeZone = TimeZone.getDefault()
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    dateFormat.timeZone = localTimeZone

    val messageDate = Calendar.getInstance(localTimeZone)
    messageDate.timeInMillis = timeStamp

    val today = Calendar.getInstance(localTimeZone)
    val yesterday = Calendar.getInstance(localTimeZone)

    yesterday.add(Calendar.DATE, -1)

    return when{

        messageDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) && messageDate.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)
        -> "Today"

        messageDate.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && messageDate.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)
        -> "Yesterday"

        else->
            dateFormat.format(Date(timeStamp))
    }
}

fun getFormattedTime(timeStamp: Long) : String {
    val localTimeZone = TimeZone.getDefault()
    val timeFormat = SimpleDateFormat("hh:mm a",Locale.getDefault())
    timeFormat.timeZone = localTimeZone

    return timeFormat.format(Date(timeStamp))
}

fun groupMessagesByDate(messages : List<ChatMessage>) : Map<String, List<ChatMessage>>{
    return messages.groupBy {
        getFormattedDate(it.timeStamp)
    }
}