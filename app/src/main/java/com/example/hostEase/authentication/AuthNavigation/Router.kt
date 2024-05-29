package com.example.hostEase.authentication.AuthNavigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen{
    object RegisterScreen : Screen()
    object LoginScreen : Screen()
    object HomeScreen : Screen()
}

object Router{
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.RegisterScreen)

    fun navigateTo(destination : Screen){
        currentScreen.value = destination
    }
}