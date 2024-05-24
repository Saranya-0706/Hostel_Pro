package com.example.HostEase.Authentication.Register

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
import com.example.HostEase.Authentication.ButtonComponent
import com.example.HostEase.Authentication.ClickableTextComponent
import com.example.HostEase.Authentication.Navigation.Router
import com.example.HostEase.Authentication.Navigation.Screen
import com.example.HostEase.Authentication.PassTextField
import com.example.HostEase.Authentication.RadioGroup
import com.example.HostEase.Authentication.TextBold
import com.example.HostEase.Authentication.TextField
import com.example.HostEase.Authentication.WelcomeImg
import com.example.HostEase.R

@Composable
fun RegisterScreen(registerViewModel: RegisterViewModel = RegisterViewModel() ){
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

            RadioGroup()

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
}

@Preview
@Composable
fun PreviewSignUp(){
    //RegisterScreen()
}