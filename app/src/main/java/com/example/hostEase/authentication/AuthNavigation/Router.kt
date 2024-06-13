package com.example.hostEase.authentication.AuthNavigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen{
    data object RegisterScreen : Screen()
    data object LoginScreen : Screen()
    data object HomeScreen : Screen()
}

object Router{
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.RegisterScreen)

    fun navigateTo(destination : Screen){
        currentScreen.value = destination
    }
}