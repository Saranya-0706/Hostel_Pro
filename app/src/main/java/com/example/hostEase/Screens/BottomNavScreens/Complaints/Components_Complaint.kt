package com.example.hostEase.Screens.BottomNavScreens.Complaints

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostEase.R
import com.example.hostEase.Screens.BottomNavScreens.getTimeAgo
import com.example.hostEase.Screens.HighlightedText
import com.google.firebase.auth.FirebaseUser

@Composable
fun ComplaintsItem(searchValue : String, complaint: Complaint,viewModel: ComplaintViewModel,
                   userRole : String?, currentTime : Long, currentUser : FirebaseUser? ,
                   canDelete : Boolean, onUpvote : (String) -> Unit, onDownvote : (String) -> Unit
){
    val context = LocalContext.current
    val colorPrim = colorResource(id = R.color.primaryColor)
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
    var showEmail by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                Column {
                    ClickableText(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = colorResource(id = R.color.primaryColor))){
                                append(complaint.userName)
                            }
                        }, onClick = {
                            showEmail = !showEmail
                        }
                    )

                    if (showEmail)
                    Text(text = complaint.email)
                }
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(8.dp))

            Divider()

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(2.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

                HighlightedText(text = complaint.content, searchValue = searchValue, modifier = Modifier.weight(1f), fontSize = 22.sp)

                if (canDelete){
                    IconButton(onClick = {
                        showDeleteDialog = true
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Complaint")
                    }
                }
                if (showDeleteDialog){
                    DeleteAlertDialogComplaint(
                        onDismiss = { showDeleteDialog = false} ,
                        onComplete = {
                            viewModel.deleteComplaint(complaint.id, onComplete = {success->
                                if (success)
                                    Toast.makeText(context,"Complaint Deleted", Toast.LENGTH_SHORT).show()
                                else
                                    Toast.makeText(context,"Complaint Deletion Failed", Toast.LENGTH_SHORT).show()
                            })
                            showDeleteDialog = false
                        },
                        heading = complaint.heading
                    )
                }
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(5.dp))


            HighlightedText(text = complaint.content, searchValue = searchValue, modifier = Modifier , fontSize = 16.sp )

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(10.dp))

            Row (verticalAlignment = Alignment.CenterVertically){

                if (complaint.type == "Public") {
                    IconButton(onClick = { onUpvote(complaint.id) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.vote_up),
                            contentDescription = "Upvote",
                            tint = if (complaint.voters[currentUser?.uid] == true) colorPrim else Color.White
                        )
                    }
                    Text(text = complaint.upVotes.toString())

                    Spacer(modifier = Modifier.width(16.dp))

                    IconButton(onClick = { onDownvote(complaint.id) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.vote_up),
                            contentDescription = "DownVote",
                            modifier = Modifier.graphicsLayer(rotationZ = 180f),
                            tint = if (complaint.voters[currentUser?.uid] == false) colorPrim else Color.White
                        )
                    }
                    Text(text = complaint.downVotes.toString())
                }

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
                    Text(text = getTimeAgo(complaint.timeStamp,currentTime), style = TextStyle(
                        fontSize = 12.sp
                    ))
                }
            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddComplaintDialog(viewModel: ComplaintViewModel,onDismiss : () -> Unit, onComplete : () -> Unit){
    var heading by remember{ mutableStateOf("") }
    var content by remember{ mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var type by remember { mutableStateOf("") }
    var showTypeWarning by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(text = "Add Complaint")},
        text = {
            Column (Modifier.verticalScroll(scrollState)){

                if (showTypeWarning){
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp))
                    if (type == "Public") {
                        Text(
                            text = "Public Complaints should be of Common concern and can be viewed by Everyone",
                            color = Color.Red
                        )
                    }
                    else if (type == "Private") {
                        Text(
                            text = "Private Complaints should be of Personal concern and can be viewed by only by Admin",
                            color = Color.Red
                        )
                    }else{
                        Text(
                            text = "Select Complaint Type",
                            color = Color.Red
                        )
                    }

                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp))
                }
               Box(modifier = Modifier.fillMaxWidth(),
                   contentAlignment = Alignment.Center) {
                   ExposedDropdownMenuBox(
                       expanded = expanded,
                       onExpandedChange = { expanded = it }) {
                       OutlinedTextField(value = type, onValueChange = {}, readOnly = true,
                           label = { Text(text = "Select Complaint Type") },
                           trailingIcon = {
                               ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                           },
                           modifier = Modifier
                               .fillMaxWidth()
                               .menuAnchor()
                       )
                       ExposedDropdownMenu(
                           expanded = expanded,
                           onDismissRequest = { expanded = false }) {
                           DropdownMenuItem(text = { Text(text = "Public") }, onClick = {
                               type = "Public"
                               showTypeWarning = true
                               expanded = false
                           })
                           DropdownMenuItem(text = { Text(text = "Private") }, onClick = {
                               type = "Private"
                               showTypeWarning = true
                               expanded = false
                           })
                       }
                   }
                   }

                   OutlinedTextField(
                       value = heading,
                       onValueChange = { heading = it },
                       label = { Text(text = "Heading") })
                   OutlinedTextField(
                       value = content,
                       onValueChange = { content = it },
                       label = { Text(text = "Content") })

            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            Button(onClick = {
                if (type.isNotEmpty()) {
                    viewModel.addComplaint(
                        Complaint(
                            heading = heading,
                            content = content,
                            type = type
                        ), onComplete = { success ->
                            if (success) {
                                onComplete()
                                Toast.makeText(
                                    context,
                                    "Complaint Added Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else
                                Toast.makeText(
                                    context,
                                    "Failed to Add Complaint! Please Try Again",
                                    Toast.LENGTH_SHORT
                                ).show()
                        }
                    )
                }else
                    showTypeWarning = true
            }) {
                Text(text = "Add")
            }
        })
}

@Composable
fun DeleteAlertDialogComplaint(onDismiss: () -> Unit, onComplete: () -> Unit, heading :String){

    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(text = "Delete Complaint")},
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Want to delete : ", fontSize = 18.sp )
                Text(text = heading, fontSize = 15.sp)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            Button(onClick = onComplete) {
                Text(text = "Confirm")
            }
        })
}