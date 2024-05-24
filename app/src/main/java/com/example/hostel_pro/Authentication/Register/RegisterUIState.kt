package com.example.hostel_pro.Authentication.Register

data class RegisterUIState(

    var userName : String = "",
    var email : String = "",
    var password : String = "",
    var userRole : String = "",

    var userNameError : Boolean =  false,
    var emailError : Boolean =  false,
    var passwordError : Boolean =  false,
)