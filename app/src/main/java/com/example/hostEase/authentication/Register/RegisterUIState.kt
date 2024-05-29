package com.example.hostEase.authentication.Register

data class RegisterUIState(

    var userName : String = "",
    var email : String = "",
    var password : String = "",
    var userRole : String = "",

    var userNameError : Boolean =  false,
    var emailError : Boolean =  false,
    var passwordError : Boolean =  false,
)