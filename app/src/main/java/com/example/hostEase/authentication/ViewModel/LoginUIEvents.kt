package com.example.hostEase.authentication.ViewModel

import android.content.Context

sealed class LoginUIEvent {

    data class emailEdited(val email:String) : LoginUIEvent()
    data class passwordEdited(val pass:String) : LoginUIEvent()
    data class LoginBtnClick(val context: Context) : LoginUIEvent()
}