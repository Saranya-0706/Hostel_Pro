package com.example.hostEase.Screens.NavDrawerScreens.Settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph
import com.example.hostEase.Screens.NavDrawerScreens.Settings.ChangePassDialog
import com.example.hostEase.authentication.AuthNavigation.Router
import com.example.hostEase.authentication.AuthNavigation.Screen
import com.example.hostEase.authentication.Repository.AuthRepository

@Composable
fun Settings() {
    var showChangePassDialog by remember {
        mutableStateOf(false)
    }
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){

        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)){

            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter) {
                Text(text = "SETTINGS",
                    style = TextStyle(fontSize = 20.sp),
                    modifier = Modifier.padding(top = 30.dp, bottom = 40.dp))
            }

            Button(onClick = {
                AuthRepository().logout()
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "LOG OUT", modifier = Modifier.padding(5.dp))
            }


            //Change Password
            Button(onClick = { showChangePassDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "CHANGE PASSWORD", modifier = Modifier.padding(5.dp))
            }

            if (showChangePassDialog){
                ChangePassDialog(onDismiss = { showChangePassDialog = false }, onChangePass = {newPass->
                    AuthRepository().changePass(newPass){success->
                        if (success){
                            showChangePassDialog = false
                            Toast.makeText(context,"Password Changed Successfully :) ",Toast.LENGTH_SHORT).show()
                        }
                        else
                            Toast.makeText(context,"Password Changed Failed! Try Again",Toast.LENGTH_SHORT).show()
                    }
                })
            }


            //Delete account
            Button(onClick = {  showDeleteDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "DELETE ACCOUNT", modifier = Modifier.padding(5.dp))
            }

            if (showDeleteDialog){
                DeleteAccDialog(onDismiss = { showDeleteDialog = false }, onDelete = {
                    AuthRepository().deleteAccount(onComplete = { success->
                        if (success){
                            Router.navigateTo(Screen.RegisterScreen)
                            showDeleteDialog = false
                            Toast.makeText(context,"Account Deleted Successfully ",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(context,"Account Deletion failed",Toast.LENGTH_SHORT).show()
                        }
                    })
                })
            }
        }
    }
}