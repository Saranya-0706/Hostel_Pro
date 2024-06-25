package com.example.hostEase.Screens.BottomNavScreens.Announcements

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.hostEase.R
import com.example.hostEase.Screens.BottomNavScreens.getTimeAgo

@Composable
fun AnnouncementItem(announcement: Announcement, viewModel: AnnouncementViewModel, userRole : String?, currentTime : Long){
    val context = LocalContext.current
    val colorPrim = colorResource(id = R.color.primaryColor)
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
   Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
       Column (
           modifier = Modifier
               .fillMaxWidth()
               .padding(15.dp)
       ){
           Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
               Text(text = announcement.heading, modifier = Modifier.weight(1f), style = TextStyle(
                   fontSize = 22.sp,
                   // fontWeight = FontWeight.SemiBold
               ))

               if (userRole!= "Student"){
                   IconButton(onClick = {
                       showDeleteDialog = true
                   }) {
                       Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Announcement")
                   }
               }
               if (showDeleteDialog){
                   DeleteAlertDialog(
                       onDismiss = { showDeleteDialog = false} ,
                       onComplete = {
                           viewModel.deleteAnnouncement(announcement.id, onComplete = {success->
                               if (success)
                                   Toast.makeText(context,"Announcement Deleted",Toast.LENGTH_SHORT).show()
                               else
                                   Toast.makeText(context,"Announcement Deletion Failed",Toast.LENGTH_SHORT).show()
                           })
                           showDeleteDialog = false
                       },
                       heading = announcement.heading
                   )
               }
           }

           Spacer(modifier = Modifier
               .fillMaxWidth()
               .height(5.dp))

           Divider()

           Spacer(modifier = Modifier
               .fillMaxWidth()
               .height(8.dp))


           Text(text = announcement.content, style = TextStyle(
               fontSize = 16.sp
           ))

           Spacer(modifier = Modifier
               .fillMaxWidth()
               .height(8.dp))

           announcement.attachmentUrl?.let {url->
               Column(modifier = Modifier.fillMaxWidth()) {

                   Text(text = announcement.attachmentUrlHeader.toString(), style = TextStyle(
                       fontSize = 15.sp
                   ))

                   Spacer(modifier = Modifier
                       .fillMaxWidth()
                       .height(5.dp))

                   ClickableText(
                       text = buildAnnotatedString {
                           pushStringAnnotation(tag = "URL", annotation = url)
                           withStyle(style = SpanStyle(color = colorResource(id = R.color.primaryColor))){
                               append(url)
                           }
                           pop()
                       }, onClick = {offset->
                           val annotations = buildAnnotatedString {
                               pushStringAnnotation(tag = "URL", annotation = url)
                               withStyle(style = SpanStyle(color = colorPrim)){
                                   append(url)
                               }
                               pop()
                           }.getStringAnnotations(tag = "URL", start = offset, end = offset)

                           annotations.firstOrNull()?.let {annotation ->
                               try {
                                   val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                                   ContextCompat.startActivity(context,intent,null)
                               }catch (e : Exception){
                                   Toast.makeText(context,"Invalid URL",Toast.LENGTH_SHORT).show()
                               }

                           }
                       })
               }
           }

           Spacer(modifier = Modifier
               .fillMaxWidth()
               .height(5.dp))

           Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
               Text(text = getTimeAgo(announcement.timeStamp,currentTime), style = TextStyle(
                   fontSize = 12.sp
               ))
           }

       }
   }
}

@Composable
fun AddAnnouncementDialog(viewModel: AnnouncementViewModel, onDismiss : () -> Unit, onComplete : () -> Unit){
    var heading by remember{ mutableStateOf("") }
    var content by remember{ mutableStateOf("") }
    var attachmentUri by remember{ mutableStateOf("") }
    var UriHeading by remember{ mutableStateOf("") }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(text = "Add Announcement")},
        text = {
               Column (Modifier.verticalScroll(scrollState)){

                   OutlinedTextField(value = heading, onValueChange = { heading = it}, label = { Text(text = "Heading")})
                   OutlinedTextField(value = content, onValueChange = { content = it}, label = { Text(text = "Content")})
                   OutlinedTextField(value = UriHeading, onValueChange = { UriHeading = it}, label = { Text(text = "Link Heading")})
                   OutlinedTextField(value = attachmentUri, onValueChange = { attachmentUri = it}, label = { Text(text = "Link")})

               }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            Button(onClick = {
                viewModel.addAnnouncement(
                    Announcement(
                    heading = heading,
                    content = content,
                    attachmentUrlHeader = UriHeading,
                    attachmentUrl = attachmentUri
                ), onComplete = {success->
                    if (success){
                        onComplete()
                        Toast.makeText(context,"Announcement Added Successfully",Toast.LENGTH_SHORT).show()
                    }
                    else
                        Toast.makeText(context,"Failed to Add Announcement! Please Try Again",Toast.LENGTH_SHORT).show()
                }
                )
            }) {
                Text(text = "Add")
            }
        })

}

@Composable
fun DeleteAlertDialog(onDismiss: () -> Unit, onComplete: () -> Unit, heading :String){

    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(text = "Delete Announcement")},
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
