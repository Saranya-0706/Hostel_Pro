package com.example.hostEase.authentication.ViewModel

import android.content.Context

sealed class RegisterUIEvent {

    data class userNameEdited(val userName:String) : RegisterUIEvent()
    data class emailEdited(val email:String) : RegisterUIEvent()
    data class passwordEdited(val pass:String) : RegisterUIEvent()
    data class confirmPassEdited(val confirmPass:String) : RegisterUIEvent()

    data class RegisterBtnClick(val context: Context) : RegisterUIEvent()
}