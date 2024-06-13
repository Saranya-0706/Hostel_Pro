package com.example.hostEase.authentication

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hostEase.Navigation_App.view.HomeScreen
import com.example.hostEase.Navigation_App.viewmodel.HomeViewModel
import com.example.hostEase.authentication.AuthNavigation.Router
import com.example.hostEase.authentication.AuthNavigation.Screen
import com.example.hostEase.authentication.View.LoginScreen
import com.example.hostEase.authentication.View.RegisterScreen

@Composable
fun Authentication(homeViewModel: HomeViewModel = viewModel()){

    homeViewModel.ActiveSessionCheck()
    Surface (
        modifier = Modifier.fillMaxSize()
    ){

        if(homeViewModel.isUserLoggedIn.value == true){
            Router.navigateTo(Screen.HomeScreen)
        }

        Crossfade(targetState = Router.currentScreen) { currentState ->
            when(currentState.value){
                Screen.LoginScreen -> LoginScreen()
                Screen.RegisterScreen -> RegisterScreen()
                Screen.HomeScreen -> HomeScreen()
            }

        }
    }
}