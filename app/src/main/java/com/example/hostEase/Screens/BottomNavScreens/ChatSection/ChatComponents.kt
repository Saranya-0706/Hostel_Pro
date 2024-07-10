package com.example.hostEase.Screens.BottomNavScreens.ChatSection

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hostEase.R
import com.example.hostEase.Screens.BottomNavScreens.getFormattedTime
import kotlinx.coroutines.delay

@Composable
fun TextBox(message: ChatMessage, chatViewModel: ChatViewModel){

    val currentUser by chatViewModel.currentUser.collectAsStateWithLifecycle()

    var currentTime by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var expanded by remember { mutableStateOf(false) }
    var showEmail by remember { mutableStateOf(false) }

    Row (modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if(currentUser?.uid == message.senderId) Arrangement.End else Arrangement.Start){

        Card(modifier = Modifier
            .wrapContentSize()
            .padding(5.dp)
            .padding(top = 0.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = { expanded = !expanded })
            },
            elevation = CardDefaults.cardElevation(4.dp),
        ) {
            Column(modifier = Modifier
                .padding(10.dp)) {
                ClickableText(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = colorResource(id = R.color.primaryColor))) {
                            append("${message.senderName}         ")
                        }
                    }, onClick = {
                        showEmail = !showEmail
                    }
                )

                if (showEmail)
                    Text(text = message.senderEmail)

                Spacer(modifier = Modifier.height(6.dp))

                Text(text = message.message, fontSize = 16.sp)

                Spacer(modifier = Modifier.height(6.dp))


                Box( modifier = Modifier.align(Alignment.End)) {
                    Text(text = getFormattedTime(message.timeStamp), fontSize = 10.sp)
                }
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Edit") },
                    onClick = {
                        expanded = false
                        //TO DO implement edit
                    })

                DropdownMenuItem(
                    text = { Text(text = "Delete") },
                    onClick = {
                        expanded = false
                        chatViewModel.deleteMessage(message.senderRole,message.hostel,message.id)
                    })

            }
        }
    }



    LaunchedEffect(Unit) {
        while (true) {
            delay(10*1000)
            currentTime = System.currentTimeMillis()
        }
    }

}


@Composable
fun DateHeader(date : String){

    Box(modifier = Modifier.fillMaxWidth().padding(5.dp), contentAlignment = Alignment.Center)
    {
        Card(
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(
                text = " $date ",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}