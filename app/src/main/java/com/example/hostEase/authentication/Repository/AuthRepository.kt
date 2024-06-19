package com.example.hostEase.authentication.Repository

import com.example.hostEase.authentication.AuthNavigation.Router
import com.example.hostEase.authentication.AuthNavigation.Screen
import com.example.hostEase.authentication.ViewModel.LoginViewModel
import com.example.hostEase.authentication.ViewModel.RegisterViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AuthRepository() {

    private val database :DatabaseReference = FirebaseDatabase.getInstance().reference
    fun register(userName : String, userRole : String, email : String, password : String, registerViewModel: RegisterViewModel = RegisterViewModel()) {

        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                registerViewModel.regProgress.value = false
                if(it.isSuccessful){

                    val user = FirebaseAuth.getInstance().currentUser
                    user?.let{
                        val userId = it.uid
                        val userMap = mapOf(
                            "username" to userName,
                            "email" to email,
                            "role" to userRole,
                            "phone" to "",
                            "hostel" to "",
                            "profileImgUrl" to ""
                        )

                        database.child("users").child(userId).setValue(userMap).addOnCompleteListener {

                        }
                    }
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

        Router.navigateTo(Screen.LoginScreen)
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

    fun changePass(newPass :String, onComplete : (Boolean) -> Unit){

        FirebaseAuth.getInstance()
            .currentUser?.let {user ->
                user.updatePassword(newPass)
                    .addOnCompleteListener {task->
                        if(task.isSuccessful)
                            onComplete(true)
                        else
                            onComplete(false)
                    }
            }
    }

}