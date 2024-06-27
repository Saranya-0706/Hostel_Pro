package com.example.hostEase.Navigation_App.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hostEase.Navigation_App.navigation.NavDrawerItems
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel : ViewModel() {

    val navDrawerItems = listOf(
        NavDrawerItems.Profile,
        NavDrawerItems.Settings,
        NavDrawerItems.PrivateComplaints,
        NavDrawerItems.c
    )

    val isUserLoggedIn : MutableLiveData<Boolean> = MutableLiveData()

    fun ActiveSessionCheck(){
        if (FirebaseAuth.getInstance().currentUser != null){
            isUserLoggedIn.value = true
        }
        else{
            isUserLoggedIn.value =  false
        }
    }
}