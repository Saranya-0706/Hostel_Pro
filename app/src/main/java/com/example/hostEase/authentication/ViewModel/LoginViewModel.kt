package com.example.hostEase.authentication.ViewModel

import android.widget.Toast
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

            is LoginUIEvent.LoginBtnClick -> {
                AuthRepository().login(
                    email = loginUIState.value.email,
                    password = loginUIState.value.password,
                    onComplete = {success, error->
                        if(success)
                            Toast.makeText(event.context,"Login Successful",Toast.LENGTH_SHORT).show()
                        else{
                            if (error?.isNotEmpty() == true)
                                Toast.makeText(event.context,error,Toast.LENGTH_SHORT).show()
                            else
                                Toast.makeText(event.context,"Login Failed!",Toast.LENGTH_SHORT).show()
                        }
                    }
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