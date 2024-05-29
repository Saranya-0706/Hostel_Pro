package com.example.hostEase.authentication

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.hostEase.HomeScreen
import com.example.hostEase.authentication.AuthNavigation.Router
import com.example.hostEase.authentication.AuthNavigation.Screen
import com.example.hostEase.authentication.View.LoginScreen
import com.example.hostEase.authentication.View.RegisterScreen

@Composable
fun Authentication(){
    Surface (
        modifier = Modifier.fillMaxSize()
    ){

        Crossfade(targetState = Router.currentScreen) { currentState ->
            when(currentState.value){
                Screen.LoginScreen -> LoginScreen()
                Screen.RegisterScreen -> RegisterScreen()
                Screen.HomeScreen -> HomeScreen()
            }

        }
    }
}