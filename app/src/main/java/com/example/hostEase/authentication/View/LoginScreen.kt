package com.example.hostEase.authentication.View

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostEase.authentication.ButtonComponent
import com.example.hostEase.authentication.ClickableTextComponent
import com.example.hostEase.authentication.AuthNavigation.Router
import com.example.hostEase.authentication.AuthNavigation.Screen
import com.example.hostEase.authentication.PassTextField
import com.example.hostEase.authentication.TextBold
import com.example.hostEase.authentication.TextField
import com.example.hostEase.authentication.WelcomeImg
import com.example.hostEase.R
import com.example.hostEase.Screens.NavDrawerScreens.Settings.ForgotPassDialog
import com.example.hostEase.authentication.ViewModel.LoginUIEvent
import com.example.hostEase.authentication.ViewModel.LoginViewModel

@Composable
fun LoginScreen(loginViewModel :  LoginViewModel = LoginViewModel()){

    var showDialog by remember{
        mutableStateOf(false)
    }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

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

                TextField(
                    label = stringResource(id = R.string.email),
                    painter = painterResource(id = R.drawable.email_svgrepo_com),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.emailEdited(it))

                    },
                    errorStatus = loginViewModel.loginUIState.value.emailError)
                PassTextField(label = stringResource(id = R.string.pass),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.passwordEdited(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.passwordError)

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp))

                ClickableText(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = colorResource(id = R.color.primaryColor))){
                            append("Forgot Password ?")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    style = TextStyle(fontSize = 15.sp)
                ) {
                    showDialog = true
                }
                if(showDialog){
                    ForgotPassDialog(onDismiss = { showDialog =  false}) {
                        showDialog = false
                        Toast.makeText(context,"Password Reset email sent Successfully!", Toast.LENGTH_SHORT).show()
                    }
                }

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp))

                ButtonComponent(value = stringResource(id = R.string.login), onButtonClick = {
                    loginViewModel.onEvent(LoginUIEvent.LoginBtnClick(context))
                },
                    isEnabled = loginViewModel.allValidationsSuccess.value
                )

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp))

                ClickableTextComponent (loginClick = false, onTextSelected = {

                    Router.navigateTo(Screen.RegisterScreen)
                })
            }
        }

        if(loginViewModel.loginProgress.value){
            //CircularProgressIndicator()
        }
    }

}

@Preview
@Composable
fun PreviewSignIn(){
    LoginScreen()
}