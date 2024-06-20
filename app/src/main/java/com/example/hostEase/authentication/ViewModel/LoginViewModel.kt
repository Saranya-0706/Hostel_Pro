package com.example.hostEase.authentication.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.hostEase.authentication.AuthValidation.Validation
import com.example.hostEase.authentication.Repository.AuthRepository

class LoginViewModel : ViewModel() {

    var loginUIState = mutableStateOf(LoginUIState())

    var allValidationsSuccess = mutableStateOf(false)

    var loginProgress = mutableStateOf(false)

    fun onEvent(event :LoginUIEvent){
        when(event){
            is LoginUIEvent.emailEdited -> {
                loginUIState.value = loginUIState.value.copy(email = event.email)
            }
            is LoginUIEvent.passwordEdited -> {
                loginUIState.value = loginUIState.value.copy(password = event.pass)
            }
            LoginUIEvent.LoginBtnClick -> {
                AuthRepository().login(
                    email = loginUIState.value.email,
                    password = loginUIState.value.password
                )
                loginProgress.value = true
            }

        }
        validateLoginWithRules()
    }
    private fun validateLoginWithRules(){
        val emailResult = Validation.validateEmail(
            email = loginUIState.value.email
        )

        val passwordResult = Validation.validatePassword(
            password = loginUIState.value.password
        )

        loginUIState.value = loginUIState.value.copy(
            emailError = !emailResult.status,
            passwordError = !passwordResult.status
        )

        allValidationsSuccess.value = emailResult.status && passwordResult.status
    }
}