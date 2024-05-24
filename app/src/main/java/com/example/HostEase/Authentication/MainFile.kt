package com.example.HostEase.Authentication

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.HostEase.Authentication.Navigation.Router
import com.example.HostEase.Authentication.Navigation.Screen
import com.example.HostEase.Authentication.Login.LoginScreen
import com.example.HostEase.Authentication.Register.RegisterScreen

@Composable
fun Authentication(){
    Surface (
        modifier = Modifier.fillMaxSize()
    ){

        Crossfade(targetState = Router.currentScreen) { currentState ->
            when(currentState.value){
                Screen.LoginScreen -> LoginScreen()
                Screen.RegisterScreen -> RegisterScreen()
            }

        }
    }
}