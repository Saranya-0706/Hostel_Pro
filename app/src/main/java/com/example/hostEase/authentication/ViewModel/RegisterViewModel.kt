package com.example.hostEase.authentication.ViewModel

import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.hostEase.authentication.AuthValidation.AdminEmails
import com.example.hostEase.authentication.AuthValidation.Validation
import com.example.hostEase.authentication.Repository.AuthRepository
import kotlin.coroutines.coroutineContext

class RegisterViewModel : ViewModel() {

    var regUIState = mutableStateOf(RegisterUIState())

    var allValidationsSuccess = mutableStateOf(false)

    var isAdminEmail = mutableStateOf(false)

    var regProgress = mutableStateOf(false)

    fun onUIEvent(event : RegisterUIEvent){
        when(event){
            is RegisterUIEvent.userNameEdited -> {
                regUIState.value = regUIState.value.copy(userName = event.userName)
            }

            is RegisterUIEvent.emailEdited -> {
                regUIState.value = regUIState.value.copy(email = event.email)
                isAdminEmail.value = AdminEmails.isAdminEmail(email = event.email)

            }

            is RegisterUIEvent.passwordEdited -> {
                regUIState.value = regUIState.value.copy(password = event.pass)

            }

            is RegisterUIEvent.userRoleEdited -> {
                regUIState.value = regUIState.value.copy(userRole = event.role)
            }

            is RegisterUIEvent.RegisterBtnClick -> {
                AuthRepository().register(
                    userName = regUIState.value.userName,
                    userRole = regUIState.value.userRole,
                    email = regUIState.value.email,
                    password = regUIState.value.password,
                    onComplete = {success, error->
                        if(success)
                            Toast.makeText(event.context,"Registration Successful", Toast.LENGTH_SHORT).show()
                        else{
                            if (error?.isNotEmpty() == true)
                                Toast.makeText(event.context, error, Toast.LENGTH_SHORT).show()
                            else
                                Toast.makeText(event.context,"Registration Failed!", Toast.LENGTH_SHORT).show()
                        }

                    })
                regProgress.value = true
            }
        }

        validationWithRules()

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