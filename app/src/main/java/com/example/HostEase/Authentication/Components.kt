package com.example.HostEase.Authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.HostEase.R


@Composable
fun TextBold(value : String){
    Text(
        text = value,
        color = colorResource(id = R.color.primaryColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        style = TextStyle(
            fontSize = 25.sp,
            fontWeight =  FontWeight.Bold

        )
    )
}

@Composable
fun WelcomeImg(painter: Painter, contentDesc : String){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
            .height(200.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ){
        Image(
            painter = painter,
            contentDescription = contentDesc,
            contentScale = ContentScale.Crop,
        )
    }

}

@Composable
fun TextField(label : String, painter : Painter, onTextSelected: (String) -> Unit, errorStatus : Boolean = false){
    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        label = { Text(text = label, textAlign = TextAlign.Center) },
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .padding(5.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painter, contentDescription ="" )
        },
        isError = errorStatus
    )
}

@Composable
fun PassTextField(label : String, onTextSelected: (String) -> Unit, errorStatus : Boolean = false){
    val password = remember {
        mutableStateOf("")
    }

    val passVisibility = remember {
        mutableStateOf(false)
    }

    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        label = { Text(text = label, textAlign = TextAlign.Center) },
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .padding(5.dp),
        value = password.value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        singleLine = true,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.passwrd), contentDescription ="" )
        },
        trailingIcon = {
            IconButton(onClick = { passVisibility.value = !passVisibility.value}) {
                if(passVisibility.value){
                    Icon(painter = painterResource(id = R.drawable.show), contentDescription = "")
                }else{
                    Icon(painter = painterResource(id = R.drawable.hide), contentDescription = "")
                }
            }
        },
        visualTransformation = if(passVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = errorStatus

    )
}

@Composable
fun RadioGroup(){
    var role by remember {
        mutableStateOf("Student")
    }

    Row (modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.White)){

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = role == "Student",
                onClick = {
                    role = "Student"
                })
            Text(text = "Student",style = TextStyle(
                fontSize = 15.sp
            )
            )
        }
        Spacer(modifier = Modifier
            .width(45.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = role == "Admin",
                onClick = {
                    role = "Admin"
                })
            Text(text = "Admin",style = TextStyle(
                fontSize = 15.sp
            )
            )
        }

    }
}

@Composable
fun ButtonComponent(value: String, onButtonClick : () -> Unit, isEnabled : Boolean = false){
    Button(
        onClick = {
            onButtonClick.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        enabled = isEnabled
    ){
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        colorResource(id = R.color.primaryColor),
                        colorResource(id = R.color.SecondaryColor)
                    )
                ),
                shape = RoundedCornerShape(30.dp)
            ),
            contentAlignment = Alignment.Center
        ) {

            Text(text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ClickableTextComponent(loginClick:Boolean = true, onTextSelected: (String) -> Unit){

    val initialTxt = if(loginClick) stringResource(id = R.string.loginTxt) else stringResource(id = R.string.registerTxt)
    val loginRegTxt = if(loginClick)" Login" else " Register"

    val annotatedString = buildAnnotatedString {
        append(initialTxt)
        withStyle(style = SpanStyle(color = colorResource(id = R.color.primaryColor))){
            pushStringAnnotation(tag = loginRegTxt, annotation = loginRegTxt )
            append(loginRegTxt)
        }
    }

    ClickableText(
        text = annotatedString,
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        style = TextStyle(fontSize = 15.sp, textAlign = TextAlign.Center)
    ) {
        onTextSelected(loginRegTxt)
    }
}


