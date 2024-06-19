package com.example.hostEase.Screens.NavDrawerScreens.Settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.hostEase.R
import com.example.hostEase.authentication.AuthValidation.Validation
import com.example.hostEase.authentication.PassTextField
import com.example.hostEase.authentication.TextField
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ChangePassDialog(
    onDismiss : () -> Unit,
    onChangePass : (String) -> Unit
){
    var email by remember { mutableStateOf("") }
    var currentPass by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }
    var errorStatusOld by remember { mutableStateOf( true) }
    var errorStatusNew by remember { mutableStateOf( true) }
    var showProgress by remember {
        mutableStateOf(false)
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface (
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(15.dp)) {

                Text(text = "Change Password",style = TextStyle(fontSize = 20.sp))

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp))

                TextField(label = "Email", painter = painterResource(id = R.drawable.email_svgrepo_com) , onTextSelected ={
                    email = it
                } )

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

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp))

                Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                    Button(onClick = onDismiss) {
                        Text(text = "Cancel")
                    }

                    Button(onClick = {

                        if (newPassword == confirmPass){

                            showProgress = true

                            val user = FirebaseAuth.getInstance().currentUser
                            if (user != null && user.email != null && user.email == email){
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
                }

            }

            if (showProgress)
            {
                //CircularProgressIndicator()
            }
        }

    }
}
