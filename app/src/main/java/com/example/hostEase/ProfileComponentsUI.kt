package com.example.hostEase

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileTextComponent(text : String){
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp),
        style = TextStyle(
            fontSize = 20.sp,

        )
    )
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

