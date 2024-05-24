package com.example.hostel_pro.Authentication.Register

sealed class RegisterUIEvent {

    data class userNameEdited(val userName:String) : RegisterUIEvent()
    data class emailEdited(val email:String) : RegisterUIEvent()
    data class passwordEdited(val pass:String) : RegisterUIEvent()
    data class userRoleEdited(val role:String) : RegisterUIEvent()

    object  RegisterBtnClick : RegisterUIEvent()
}