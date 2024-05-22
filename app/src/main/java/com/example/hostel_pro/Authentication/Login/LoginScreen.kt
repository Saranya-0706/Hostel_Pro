package com.example.hostel_pro.Authentication.Login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostel_pro.Authentication.ButtonComponent
import com.example.hostel_pro.Authentication.ClickableTextComponent
import com.example.hostel_pro.Authentication.Navigation.Router
import com.example.hostel_pro.Authentication.Navigation.Screen
import com.example.hostel_pro.Authentication.PassTextField
import com.example.hostel_pro.Authentication.TextBold
import com.example.hostel_pro.Authentication.TextField
import com.example.hostel_pro.Authentication.WelcomeImg
import com.example.hostel_pro.R

@Composable
fun LoginScreen(){

    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(25.dp)
    ){

        Column (
            modifier = Modifier.fillMaxSize()
        ){

            WelcomeImg(painter = painterResource(id = R.drawable.iitism), contentDesc = "" )

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(25.dp))

            TextBold(value = stringResource(id = R.string.login))

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp))

            Text(text = "Welcome Back !",modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(5.dp),
                style = TextStyle(fontSize = 18.sp)
            )

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(10.dp))

            TextField(label = stringResource(id = R.string.email),painter = painterResource(id = R.drawable.email_svgrepo_com), onTextSelected = {

            })
            PassTextField(label = stringResource(id = R.string.pass), onTextSelected = {

            })

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(180.dp))

            ButtonComponent(value = stringResource(id = R.string.login), onButtonClick = {

            })

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(15.dp))

            ClickableTextComponent (loginClick = false, onTextSelected = {

                Router.navigateTo(Screen.RegisterScreen)
            })
        }
    }
}

@Preview
@Composable
fun PreviewSignIn(){
    LoginScreen()
}