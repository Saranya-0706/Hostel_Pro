package com.example.hostEase.Screens.NavDrawerScreens.Profile

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostEase.R

@Composable
fun ProfileTextComponent(text : String){
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp, bottom = 2.dp)
            .border(
                1.dp,
                color = colorResource(id = R.color.primaryColor),
                RoundedCornerShape(5.dp)
            )
            .clip(RoundedCornerShape(5.dp))
            .horizontalScroll(scrollState)
    ){
        Text(
            text = text,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, top = 12.dp, bottom = 12.dp, end = 25.dp),
            style = TextStyle(
                fontSize = 20.sp,

                )
        )
    }
}

@Composable
fun ProfileTextField(label : String, onTextSelected : (String) -> Unit){
    val textValue = remember {
        mutableStateOf("")
    }
    TextField(
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        singleLine = true,
        label = { Text(text = label, textAlign = TextAlign.Center) },
        modifier = Modifier.padding(start = 25.dp)
    )
}
