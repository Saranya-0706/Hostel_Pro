package com.example.hostEase.authentication.Model

import com.example.hostEase.authentication.AuthNavigation.Router
import com.example.hostEase.authentication.AuthNavigation.Screen
import com.example.hostEase.authentication.ViewModel.LoginViewModel
import com.example.hostEase.authentication.ViewModel.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthRepository() {
    fun register(email : String, password : String, registerViewModel: RegisterViewModel = RegisterViewModel()) {

        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                registerViewModel.regProgress.value = false
                if(it.isSuccessful){
                    Router.navigateTo(Screen.HomeScreen)
                }
            }
            .addOnFailureListener {

            }
    }

    fun logout(){

        FirebaseAuth
            .getInstance()
            .signOut()
    }

    fun login(email: String,password: String, loginViewModel: LoginViewModel = LoginViewModel()){

        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {

                loginViewModel.loginProgress.value = false
                if(it.isSuccessful){
                    Router.navigateTo(Screen.HomeScreen)
                }
            }
            .addOnFailureListener {

            }
    }

}