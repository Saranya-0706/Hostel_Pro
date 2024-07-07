package com.example.hostEase.Screens.BottomNavScreens.ChatSection

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChatSwitcher(chatViewModel: ChatViewModel = viewModel()) {

    ChatScreen("Students Chat",chatViewModel)

    /*val chatOptions = listOf("Students Chat", "Student Admin Chat")
    var selectedChat by remember {
        mutableStateOf(chatOptions[0])
    }

    var expanded by remember { mutableStateOf(false) }

    Column {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .padding(top = 0.dp)
        ){
            TextButton(onClick = { expanded = !expanded }
            ) {
                Text(text = selectedChat)
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded =  false }) {
                chatOptions.forEach {chatOption->
                    DropdownMenuItem(
                        text = { Text(text = chatOption)},
                        onClick = {
                            selectedChat = chatOption
                            expanded  = false
                        })

                }
            }

        }

        when(selectedChat){
            "Students Chat" -> ChatScreen("Students Chat",chatViewModel)
            "Student Admin Chat" -> ChatScreen("Student Admin Chat", chatViewModel)

        }
    }

     */
}