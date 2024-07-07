package com.example.hostEase.Screens.NavDrawerScreens.Profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hostEase.R
import com.skydoves.landscapist.ImageOptions

@Composable
fun ProfileScreen(userViewModel: UserViewModel = viewModel(), userId : String) {

    val user by userViewModel.user.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var editing by remember { mutableStateOf(false) }
    var userName by remember { mutableStateOf("") }
    var userRole by remember { mutableStateOf("Student") }
    var phone by remember { mutableStateOf("") }
    var hostel by remember { mutableStateOf("") }
    var profileImgUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri->
        profileImgUri = uri
    }

    LaunchedEffect(Unit) {
        userViewModel.loadUserProfile()
    }

    user?.let {
        userName = it.username
        phone = it.phone
        hostel = it.hostel
        userRole = it.role
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        user?.let { it ->

            if(editing){

                profileImgUri.let {uri->
                    com.skydoves.landscapist.glide.GlideImage(
                        imageModel = { uri },
                        imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp)
                            .clip(CircleShape)
                            .border(1.dp, colorResource(id = R.color.primaryColor), CircleShape)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp))

                Button(onClick = {
                    launcher.launch("image/*")
                }, modifier = Modifier.fillMaxWidth(0.5f).align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Select Image")
                }

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp))


                ProfileTextField(label = "UserName"){
                    userName = it
                }

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp))

                ProfileTextField(label = "Phone Number"){
                    phone = it
                }

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp))

                ProfileTextField(label = "Hostel Name"){
                    hostel = it
                }

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp))

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    Button(onClick = {
                        profileImgUri.let { uri ->

                            if (uri != null) {
                                userViewModel.uploadProfileImage(
                                    uri = uri,
                                    userId = userId,
                                    onSuccess = { imgUrl ->
                                        userViewModel.updateUserProfile(
                                            userId,
                                            phone,
                                            userName,
                                            hostel,
                                            imgUrl
                                        ) {value->
                                            editing = !value
                                        }
                                    },
                                    onFailure = {

                                    }

                                )
                            } else {
                                userViewModel.updateUserProfile(
                                    userId,
                                    phone,
                                    userName,
                                    hostel,
                                    it.profileImgUrl
                                ) {value->
                                    editing = !value
                                }
                            }
                        }

                    }) {
                        Text(text = "SAVE")
                    }
                }
            }
            else{

                com.skydoves.landscapist.glide.GlideImage(
                    imageModel = { it.profileImgUrl },
                    imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                        .clip(CircleShape)
                        .border(1.dp, colorResource(id = R.color.primaryColor), CircleShape)
                        .align(Alignment.CenterHorizontally)
                )


                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp))

                ProfileTextComponent(text = "UserName :  ${it.username}")
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp))
                ProfileTextComponent(text = "Email        :  ${it.email}")
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp))
                ProfileTextComponent(text = "UserRole   :  ${it.role}")
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp))
                ProfileTextComponent(text = "Hostel      :  ${it.hostel}")
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp))
                ProfileTextComponent(text = "Phone      :  ${it.phone}")
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp))

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    Button(onClick = { editing = true }) {
                        Text(text = "EDIT")
                    }
                }

            }
        }
    }
}
