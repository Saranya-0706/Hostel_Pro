package com.example.hostEase.Screens.NavDrawerScreens

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hostEase.GlideImage
import com.example.hostEase.ProfileTextComponent
import com.example.hostEase.ProfileTextField
import com.example.hostEase.R
import com.example.hostEase.User
import com.example.hostEase.UserViewModel
import com.example.hostEase.authentication.TextBold
import com.github.dhaval2404.imagepicker.ImagePicker

@Composable
fun ProfileScreen( userViewModel: UserViewModel = viewModel(), userId : String) {

    val user by userViewModel.user.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var editing by remember { mutableStateOf(false) }
    var userName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var hostel by remember { mutableStateOf("") }
    var profileImgUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {result->
        if(result.resultCode == Activity.RESULT_OK){
            profileImgUri = result.data?.data
        }

    }

    LaunchedEffect(Unit) {
        userViewModel.loadUserProfile()
    }

    user?.let {
        userName = it.username
        phone = it.phone
        hostel = it.hostel
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        user?.let {

            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()){
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape)
                        .border(1.dp, color = colorResource(id = R.color.primaryColor), CircleShape)
                        .padding(top = 150.dp)
                        .clickable {
                            ImagePicker
                                .with(context as Activity)
                                .cropSquare()
                                .compress(1024)
                                .maxResultSize(1080, 1080)
                                .createIntent {
                                    imagePickerLauncher.launch(it)
                                }
                        },
                ){

                    profileImgUri?.let {uri->
                        GlideImage(context = context, imgUrl = uri, modifier = Modifier.fillMaxSize())
                    } ?: it.profileImgUrl?.let {url->
                        GlideImage(context = context, imgUrl = url, modifier = Modifier.fillMaxSize() )
                    } ?:
                        Image(painter = painterResource(R.drawable.iitism), contentDescription = null ,modifier = Modifier.fillMaxSize())
                }
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(40.dp))

            if(editing){

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
                        profileImgUri?.let { uri->

                            userViewModel.uploadProfileImage(userId, uri ) { imgUrl->

                                if (imgUrl!= null){
                                    userViewModel.updateUserProfile(userId,phone,userName,hostel, it.profileImgUrl){
                                        editing = !it
                                    }
                                }
                            }
                        } ?: run {
                            userViewModel.updateUserProfile(userId,phone,userName,hostel, it.profileImgUrl){
                                editing = !it
                            }
                        }
                    }) {
                        Text(text = "SAVE")
                    }
                }
            }
            else{

                ProfileTextComponent(text = "UserName : ${it.username}")
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp))
                ProfileTextComponent(text = "Email : ${it.email}")
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp))
                ProfileTextComponent(text = "UserRole : ${it.role}")
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp))
                ProfileTextComponent(text = "Hostel : ${it.hostel}")
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp))
                ProfileTextComponent(text = "Phone : ${it.phone}")
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
