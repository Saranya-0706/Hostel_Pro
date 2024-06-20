package com.example.hostEase.Screens.NavDrawerScreens.Settings

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.hostEase.R
import com.example.hostEase.authentication.AuthValidation.Validation
import com.example.hostEase.authentication.PassTextField
import com.example.hostEase.authentication.Repository.AuthRepository
import com.example.hostEase.authentication.TextField
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ChangePassDialog(
    onDismiss : () -> Unit,
    onChangePass : (String) -> Unit,
    showForgotPass : (Boolean) -> Unit
){
    var currentPass by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }
    var errorStatusOld by remember { mutableStateOf( true) }
    var errorStatusNew by remember { mutableStateOf( true) }
    var showProgress by remember {
        mutableStateOf(false)
    }

    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(text = "Change Password",style = TextStyle(fontSize = 20.sp)) },
        text = {
            Column{
                PassTextField(label = "Current Password", errorStatus = !errorStatusOld, onTextSelected = {
                    currentPass = it
                    errorMsg = ""
                    errorStatusOld = Validation.validatePassword(currentPass).status
                })

                PassTextField(label = "New Password", errorStatus = !errorStatusNew, onTextSelected = {
                    newPassword = it
                    errorMsg = ""
                    errorStatusNew = Validation.validatePassword(newPassword).status
                })

                PassTextField(label = "Confirm Password", onTextSelected = {
                    confirmPass = it
                    errorMsg = ""
                })

                if (errorMsg.isNotEmpty()){
                    showProgress = false
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp))
                    Text(text = errorMsg, color = Color.Red)
                }

                Box (
                    Modifier.clickable {
                        showForgotPass(true)
                    }
                ){
                    Text(text = "Forgot Password?",
                        color = colorResource(id = R.color.primaryColor),
                        modifier = Modifier.padding(5.dp).padding(top = 8.dp ))

                }
            }
            if (showProgress)
            {
                //CircularProgressIndicator()
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Cancel")
            }

        },
        confirmButton = {
            Button(onClick = {

                if (newPassword == confirmPass){

                    showProgress = true

                    val user = FirebaseAuth.getInstance().currentUser
                    if (user != null && user.email != null ){
                        val credential = EmailAuthProvider.getCredential(user.email!!, currentPass)
                        user.reauthenticate(credential).addOnCompleteListener { task->
                            if(task.isSuccessful)
                            {
                                onChangePass(newPassword)
                                showProgress = false
                            }
                            else
                                errorMsg = task.exception?.message.toString()
                        }
                    }
                    else
                        errorMsg = "User Authentication failed using given email and current password"
                }
                else
                    errorMsg = "Passwords do not match"
            }, enabled = errorStatusOld && errorStatusNew && newPassword.isNotEmpty() && currentPass.isNotEmpty()){
                Text(text = "Change")
            }
        })
}

@Composable
fun DeleteAccDialog(
    onDismiss : () -> Unit,
    onDelete : () -> Unit
){
    var currentPass by remember { mutableStateOf("") }
    var showProgress by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }

    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(text = "Confirm Account Deletion")},
        text = {
               Column {
                   Text(text = "Please enter your current password to confirm Account Deletion")

                   Spacer(modifier = Modifier
                       .fillMaxWidth()
                       .height(15.dp))

                   PassTextField(label = "Current Password", onTextSelected = {
                       currentPass = it
                       errorMsg = ""
                   } )

                   if (showProgress)
                   {
                       //CircularProgressIndicator()
                   }

                   if (errorMsg.isNotEmpty()){
                       showProgress = false
                       Spacer(modifier = Modifier
                           .fillMaxWidth()
                           .height(5.dp))
                       Text(text = errorMsg, color = Color.Red)
                   }
               }
        },
        confirmButton = {
            Button(onClick = {

                    showProgress = true
                    if (currentPass.isNotEmpty()) {
                        val user = FirebaseAuth.getInstance().currentUser
                        if (user != null && user.email != null) {
                            val credential = EmailAuthProvider.getCredential(user.email!!, currentPass)
                            user.reauthenticate(credential).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    showProgress = false
                                    onDelete()
                                } else
                                    errorMsg = task.exception?.message.toString()
                            }
                        } else
                            errorMsg = "Current Password entered is Incorrect"
                    }
                    else
                        errorMsg = "Password cannot be empty"
                }) {
                    Text(text = "Delete")
                }

        },
        dismissButton = {
            Button(onClick = onDismiss) {
                    Text(text = "Cancel")
                }
        })
}


@Composable
fun ForgotPassDialog(
    onDismiss: () -> Unit,
    onComplete : () -> Unit
){
    var email by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }
    var showProgress by remember { mutableStateOf(false) }
    var errorStatusEmail by remember { mutableStateOf(true) }

    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(text = "Forgot Password?")},
        text = {
            Column {
                Text(text = "Please enter your Email Address to receive a password reset link")

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp))

                TextField(label = "E-Mail", errorStatus = !errorStatusEmail, painter = painterResource(id = R.drawable.email_svgrepo_com) ,
                    onTextSelected = {
                        email = it
                        errorStatusEmail = Validation.validateEmail(it).status
                        errorMsg = ""
                } )

                if (showProgress)
                {
                //CircularProgressIndicator()
                }
                if (errorMsg.isNotEmpty()){
                    showProgress = false
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp))
                    Text(text = errorMsg, color = Color.Red)
                }
            }
        },
        dismissButton = {
           Button(onClick = onDismiss) {
                    Text(text = "Cancel")
                }
            },
        confirmButton = {
            Button(onClick = {

                showProgress = true
                if (email.isNotEmpty()) {
                    AuthRepository().forgotPassword(email){success, error->
                        if (success)
                        {
                            showProgress =  false
                            onComplete()
                        }
                        else
                            errorMsg  = error.toString()
                    }
                }
                else
                    errorMsg = "Email cannot be empty"
            }, enabled = errorStatusEmail
            ) {
                Text(text = "Submit")
            }
        })
}

@Preview
@Composable
fun del(){
    ForgotPassDialog(onDismiss = { /*TODO*/ }) {
        
    }
}

