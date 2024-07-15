package com.example.hostEase.Navigation_App.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hostEase.Navigation_App.navigation.NavDrawerItems
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    val navDrawerItems = listOf(
        NavDrawerItems.Profile,
        NavDrawerItems.Settings,
        NavDrawerItems.PrivateComplaints,
        NavDrawerItems.c
    )
    val isUserLoggedIn : MutableLiveData<Boolean> = MutableLiveData()

    private val _searchValue = MutableStateFlow("")
    val searchValue = _searchValue.asStateFlow()

    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive = _isSearchActive.asStateFlow()

    private val _isFilterMenuExpanded = MutableStateFlow(false)
    val isFilterMenuExpanded = _isFilterMenuExpanded.asStateFlow()

    fun onMenuClicked(){
        _isFilterMenuExpanded.value = !_isFilterMenuExpanded.value
    }
    fun updateSearchValue(newValue : String){
        _searchValue.value = newValue
    }

    fun activateSearch(){
        _isSearchActive.value = true
    }

    fun deactivateSearch(){
        _isSearchActive.value = false
        _searchValue.value = ""
    }
    fun ActiveSessionCheck(){
        if (FirebaseAuth.getInstance().currentUser != null){
            isUserLoggedIn.value = true
        }
        else{
            isUserLoggedIn.value =  false
        }
    }


}