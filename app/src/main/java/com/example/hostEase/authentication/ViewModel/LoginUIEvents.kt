package com.example.hostEase.authentication.ViewModel

sealed class LoginUIEvent {

    data class emailEdited(val email:String) : LoginUIEvent()
    data class passwordEdited(val pass:String) : LoginUIEvent()
    object  LoginBtnClick : LoginUIEvent()
}