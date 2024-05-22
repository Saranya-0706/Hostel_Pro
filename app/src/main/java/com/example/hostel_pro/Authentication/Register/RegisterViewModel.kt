package com.example.hostel_pro.Authentication.Register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    var regUIState = mutableStateOf(RegisterUIState())

    fun onUIEvent(event :RegisterUIEvent){
        when(event){
            is RegisterUIEvent.userNameEdited -> {
                regUIState.value = regUIState.value.copy(userName = event.userName)
            }

            is RegisterUIEvent.emailEdited -> {
                regUIState.value = regUIState.value.copy(email = event.email)
            }

            is RegisterUIEvent.passwordEdited -> {
                regUIState.value = regUIState.value.copy(userName = event.pass)
            }

            is RegisterUIEvent.userRoleEdited -> {
                regUIState.value = regUIState.value.copy(userName = event.role)
            }

            RegisterUIEvent.RegisterBtnClick -> {
                register()
            }
        }
    }

    private fun register() {

    }
}