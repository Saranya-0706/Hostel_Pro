package com.example.hostEase.authentication.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostEase.authentication.ButtonComponent
import com.example.hostEase.authentication.ClickableTextComponent
import com.example.hostEase.authentication.AuthNavigation.Router
import com.example.hostEase.authentication.AuthNavigation.Screen
import com.example.hostEase.authentication.PassTextField
import com.example.hostEase.authentication.RadioGroup
import com.example.hostEase.authentication.TextBold
import com.example.hostEase.authentication.TextField
import com.example.hostEase.authentication.WelcomeImg
import com.example.hostEase.R
import com.example.hostEase.authentication.ViewModel.RegisterUIEvent
import com.example.hostEase.authentication.ViewModel.RegisterViewModel

@Composable
fun RegisterScreen(registerViewModel: RegisterViewModel = RegisterViewModel() ){

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(25.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                WelcomeImg(painter = painterResource(id = R.drawable.iitism), contentDesc ="" )

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp))

                TextBold(value = stringResource(id = R.string.register))

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp))

                TextField(
                    label = stringResource(id = R.string.name),
                    painter = painterResource(id = R.drawable.user),
                    onTextSelected = {
                        registerViewModel.onUIEvent(RegisterUIEvent.userNameEdited(it))
                    },
                    errorStatus = registerViewModel.regUIState.value.userNameError
                )
                TextField(
                    label = stringResource(id = R.string.email),
                    painter = painterResource(id = R.drawable.email_svgrepo_com),
                    onTextSelected = {
                        registerViewModel.onUIEvent(RegisterUIEvent.emailEdited(it))
                    },
                    errorStatus = registerViewModel.regUIState.value.emailError
                )
                PassTextField(label = stringResource(id = R.string.pass), onTextSelected = {
                    registerViewModel.onUIEvent(RegisterUIEvent.passwordEdited(it))
                },
                    errorStatus = registerViewModel.regUIState.value.passwordError
                )

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp))

                Text(
                    text = " Select User Role : ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 15.dp, bottom = 5.dp),
                    style = TextStyle(
                        fontSize = 15.sp
                    )
                )

                RadioGroup(onRadioBtnSelected = {
                    registerViewModel.onUIEvent(RegisterUIEvent.userRoleEdited(it))
                },
                    isAdminEnabled = registerViewModel.isAdminEmail.value)

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp))

                ButtonComponent(value = stringResource(id = R.string.register), onButtonClick = {
                    registerViewModel.onUIEvent(RegisterUIEvent.RegisterBtnClick)
                }, isEnabled = registerViewModel.allValidationsSuccess.value
                )

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp))

                ClickableTextComponent (loginClick = true, onTextSelected = {
                    Router.navigateTo(Screen.LoginScreen)
                })

            }

        }

        if(registerViewModel.regProgress.value){
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun PreviewSignUp(){
    //RegisterScreen()
}