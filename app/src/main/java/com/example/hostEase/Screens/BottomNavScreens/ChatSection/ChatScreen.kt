package com.example.hostEase.Screens.BottomNavScreens.ChatSection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hostEase.R
import com.example.hostEase.Screens.BottomNavScreens.groupMessagesByDate

@Composable
fun ChatScreen(hostel : String,userRole : String, chatViewModel: ChatViewModel = viewModel()){

    val messages by chatViewModel.messages.collectAsStateWithLifecycle()

    var currentMsg by remember { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val groupedMessages = groupMessagesByDate(messages)


    LaunchedEffect(hostel, userRole) {
        chatViewModel.loadMessages(hostel,userRole)
    }

    LaunchedEffect(messages) {
        if (messages.isNotEmpty()){
            listState.scrollToItem(messages.size + groupedMessages.size )
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(15.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
            ) {

                groupedMessages.forEach { (date, messageList) ->

                    item {
                        DateHeader(date = date)
                    }

                    messageList.forEach {message->
                        item {
                            TextBox(
                                message = message,
                                chatViewModel = chatViewModel
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                OutlinedTextField(
                    value = currentMsg, onValueChange = { currentMsg = it }, modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                    shape = RoundedCornerShape(25.dp),
                    placeholder = { Text(text = "Message")}
                )

                val message = ChatMessage(
                    message = currentMsg.text,
                    timeStamp = System.currentTimeMillis()
                )

                IconButton(
                    onClick = {
                        chatViewModel.sendMessage(hostel,userRole, message)
                              currentMsg = TextFieldValue("") },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clip(CircleShape)
                        .background(
                            colorResource(
                                id = R.color.SecondaryColor
                            )
                        )
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send",
                        modifier = Modifier.padding(5.dp))
                }

            }
        }


    }

}

@Preview
@Composable
fun chatprev(){
    ChatScreen(hostel = "", userRole = "")
}