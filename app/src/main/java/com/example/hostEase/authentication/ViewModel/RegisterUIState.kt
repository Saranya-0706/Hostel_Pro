package com.example.hostEase.authentication.ViewModel

data class RegisterUIState(

    var userName : String = "",
    var email : String = "",
    var password : String = "",
    var confirmPass : String = "",
    var userRole : String = "",
    var userHostel : String? = null,

    var userNameError : Boolean =  false,
    var emailError : Boolean =  false,
    var passwordError : Boolean =  false,
)