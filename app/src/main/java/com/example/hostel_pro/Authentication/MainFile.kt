package com.example.hostel_pro.Authentication

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.hostel_pro.Authentication.Navigation.Router
import com.example.hostel_pro.Authentication.Navigation.Screen
import com.example.hostel_pro.Authentication.Login.LoginScreen
import com.example.hostel_pro.Authentication.Register.RegisterScreen

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