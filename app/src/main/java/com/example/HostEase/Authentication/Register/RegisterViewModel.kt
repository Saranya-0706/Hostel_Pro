package com.example.HostEase.Authentication.Register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.HostEase.Authentication.Validation

class RegisterViewModel : ViewModel() {

    var regUIState = mutableStateOf(RegisterUIState())

    var allValidationsSuccess = mutableStateOf(false)

    fun onUIEvent(event :RegisterUIEvent){
        when(event){
            is RegisterUIEvent.userNameEdited -> {
                regUIState.value = regUIState.value.copy(userName = event.userName)

            }

            is RegisterUIEvent.emailEdited -> {
                regUIState.value = regUIState.value.copy(email = event.email)

            }

            is RegisterUIEvent.passwordEdited -> {
                regUIState.value = regUIState.value.copy(password = event.pass)

            }

            is RegisterUIEvent.userRoleEdited -> {
                regUIState.value = regUIState.value.copy(userRole = event.role)
            }

            RegisterUIEvent.RegisterBtnClick -> {
                register()
            }
        }

        validationWithRules()

    }

    private fun register() {

    }

    private fun validationWithRules(){
        val userNameResult = Validation.validateUserName(
            userName = regUIState.value.userName
        )
        val emailResult = Validation.validateEmail(
            email = regUIState.value.email
        )
        val passwordResult = Validation.validatePassword(
            password = regUIState.value.password
        )

        regUIState.value = regUIState.value.copy(
            userNameError = !userNameResult.status,
            emailError = !emailResult.status,
            passwordError = !passwordResult.status
        )

        allValidationsSuccess.value = userNameResult.status && emailResult.status && passwordResult.status

    }

}