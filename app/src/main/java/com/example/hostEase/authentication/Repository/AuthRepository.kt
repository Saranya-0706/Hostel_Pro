package com.example.hostEase.authentication.Repository

import com.example.hostEase.authentication.AuthNavigation.Router
import com.example.hostEase.authentication.AuthNavigation.Screen
import com.example.hostEase.authentication.ViewModel.LoginViewModel
import com.example.hostEase.authentication.ViewModel.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AuthRepository() {

    private val database :DatabaseReference = FirebaseDatabase.getInstance().reference
    fun register(userName : String, userRole : String,userHostel : String, email : String, password : String,
                 registerViewModel: RegisterViewModel = RegisterViewModel(),
                 onComplete: (Boolean, String?) -> Unit) {

        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task->
                registerViewModel.regProgress.value = false
                if(task.isSuccessful){
                    onComplete(true,null)
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.let{
                        val userId = it.uid
                        val userMap = mapOf(
                            "username" to userName,
                            "email" to email,
                            "role" to userRole,
                            "phone" to "",
                            "hostel" to userHostel,
                            "profileImgUrl" to ""
                        )

                        database.child("users").child(userId).setValue(userMap).addOnCompleteListener {

                        }
                    }
                    Router.navigateTo(Screen.HomeScreen)

                }
                else
                    onComplete(false, task.exception?.message)
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

    fun login(email: String,password: String,
              loginViewModel: LoginViewModel = LoginViewModel(),
              onComplete: (Boolean, String?) -> Unit){

        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {task->

                loginViewModel.loginProgress.value = false
                if(task.isSuccessful){
                    onComplete(true,null)
                    Router.navigateTo(Screen.HomeScreen)
                }
                else{
                    onComplete(false, task.exception?.message)
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

    fun deleteAccount( onComplete : (Boolean) -> Unit){
        FirebaseAuth.getInstance()
            .currentUser?.let {user->
                user.delete()
                    .addOnCompleteListener {task->
                        if(task.isSuccessful)
                            onComplete(true)
                        else
                            onComplete(false)
                    }
            }
    }

    fun forgotPassword(email: String, onComplete: (Boolean, String?) -> Unit){

        FirebaseAuth.getInstance()
            .sendPasswordResetEmail(email)
            .addOnCompleteListener { task->
                if (task.isSuccessful)
                    onComplete(true,null)
                else
                    onComplete(false,task.exception?.message)
            }
    }

}