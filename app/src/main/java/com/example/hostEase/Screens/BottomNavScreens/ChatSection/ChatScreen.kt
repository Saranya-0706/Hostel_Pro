package com.example.hostEase.Screens.BottomNavScreens.ChatSection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChatScreen(chatRoomId : String, chatViewModel: ChatViewModel = viewModel()){

    val messages by chatViewModel.messages.collectAsStateWithLifecycle()

    var currentMsg by remember { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()


    LaunchedEffect(chatRoomId) {
        chatViewModel.loadMessages(chatRoomId)
    }

    Box(modifier = Modifier.fillMaxSize().padding(15.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                messages.forEach { message ->
                    TextBox(
                        senderName = message.senderName,
                        senderMail = message.senderEmail,
                        message = message.message,
                        timeStamp = message.timeStamp,
                        chatViewModel = chatViewModel
                    )

                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                OutlinedTextField(
                    value = currentMsg, onValueChange = { currentMsg = it }, modifier = Modifier
                        .weight(1f)
                        .padding(5.dp)
                )

                val message = ChatMessage(
                    message = currentMsg.text,
                    timeStamp = System.currentTimeMillis()
                )

                IconButton(
                    onClick = { 
                        chatViewModel.sendMessage(chatRoomId, message)
                              currentMsg = TextFieldValue("")},
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
                }

            }
        }


    }

}