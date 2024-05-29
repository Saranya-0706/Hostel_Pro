package com.example.hostEase.authentication

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.hostEase.authentication.Navigation.Router
import com.example.hostEase.authentication.Navigation.Screen
import com.example.hostEase.authentication.Login.LoginScreen
import com.example.hostEase.authentication.Register.RegisterScreen

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