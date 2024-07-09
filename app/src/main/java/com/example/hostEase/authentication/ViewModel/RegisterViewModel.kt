package com.example.hostEase.authentication.ViewModel

import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.hostEase.authentication.AuthValidation.Validation
import com.example.hostEase.authentication.AuthValidation.hostelAuth
import com.example.hostEase.authentication.Repository.AuthRepository

class RegisterViewModel : ViewModel() {

    var regUIState = mutableStateOf(RegisterUIState())

    var allValidationsSuccess = mutableStateOf(false)

    var regProgress = mutableStateOf(false)

    fun onUIEvent(event : RegisterUIEvent){
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

            is RegisterUIEvent.confirmPassEdited -> {
                regUIState.value = regUIState.value.copy(confirmPass = event.confirmPass)
            }

            is RegisterUIEvent.RegisterBtnClick -> {

                hostelAuth.checkEmailAndHostel(
                    email = regUIState.value.email,
                    onSuccess = {isAdmin, hostelName ->
                        if (regUIState.value.confirmPass == regUIState.value.password) {
                            if (hostelName != null) {
                                regUIState.value =
                                    regUIState.value.copy(userRole = if (isAdmin) "Admin" else "Student",
                                        userHostel = hostelName)

                                    AuthRepository().register(
                                        userName = regUIState.value.userName,
                                        userRole = regUIState.value.userRole,
                                        userHostel = regUIState.value.userHostel.toString(),
                                        email = regUIState.value.email,
                                        password = regUIState.value.password,
                                        onComplete = { success, error ->
                                            if (success)
                                                Toast.makeText(event.context, "Registration Successful", Toast.LENGTH_SHORT).show()
                                            else {
                                                if (error?.isNotEmpty() == true)
                                                    Toast.makeText(event.context, error, Toast.LENGTH_SHORT).show()
                                                else
                                                    Toast.makeText(event.context, "Registration Failed!", Toast.LENGTH_SHORT).show()
                                            }

                                        })
                                    regProgress.value = true

                            }else
                            {
                                Toast.makeText(event.context, "Email is not linked with any hostel. Contact Hostel Support", Toast.LENGTH_LONG).show()
                            }
                        }else
                            Toast.makeText(event.context, "Passwords do not Match!", Toast.LENGTH_SHORT).show() },
                    onFailure = {error->
                        Toast.makeText(event.context, error, Toast.LENGTH_LONG).show()
                    }
                )
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