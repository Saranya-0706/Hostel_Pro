package com.example.hostEase.authentication.Navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen{
    object RegisterScreen : Screen()
    object LoginScreen : Screen()
}

object Router{
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.RegisterScreen)

    fun navigateTo(destination : Screen){
        currentScreen.value = destination
    }
}