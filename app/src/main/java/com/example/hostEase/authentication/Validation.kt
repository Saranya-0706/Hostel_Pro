package com.example.hostEase.authentication

import android.util.Patterns

object Validation {

    fun validateUserName(userName : String): ValidationResult {
        return ValidationResult(
            (userName.isNotEmpty() && userName.length >= 4)
        )
    }

    fun validateEmail(email : String): ValidationResult {
        return ValidationResult(
            (email.isNotEmpty() && email.length >= 6 && isEmail(email))
        )
    }
    fun validatePassword(password : String): ValidationResult {
        return ValidationResult(
            (password.isNotEmpty() && password.length >= 4)
        )
    }

}

data class ValidationResult(
    val status :Boolean = false
)

fun isEmail( email: String) : Boolean{
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}