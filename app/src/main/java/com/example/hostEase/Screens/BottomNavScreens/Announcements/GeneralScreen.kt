package com.example.hostEase.Screens.BottomNavScreens.Announcements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hostEase.Screens.NavDrawerScreens.Profile.UserViewModel
import kotlinx.coroutines.delay

@Composable
fun GeneralScreen(viewModel: AnnouncementViewModel = viewModel(), userViewModel: UserViewModel = viewModel()) {

    val user by userViewModel.user.observeAsState()
    val announcements by viewModel.announcements.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    var userRole by remember { mutableStateOf("Student") }
    var showAddDialog by remember { mutableStateOf(false) }
    var currentTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    LaunchedEffect(Unit) {
        userViewModel.loadUserProfile()
        viewModel.loadAnnouncements()
    }
    userRole = user?.role ?: "Student"

    Scaffold (
        floatingActionButton = {
            /*Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd){
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    */if (userRole == "Admin" ){
                        FloatingActionButton(onClick = { showAddDialog = true }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Announcement")
                        }
                    }

                    /*FloatingActionButton(onClick = { viewModel.loadAnnouncements()}) {
                        if (loading){
                            //CircularProgressIndicator()
                        }else
                            Icon(imageVector = Icons.Default.Refresh, contentDescription ="reload" )
                    }*/

               // }
           // }

        }
    ){
        val padding = it
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(start = 12.dp, end = 12.dp, bottom = 5.dp)
            .verticalScroll(scrollState)){
            announcements.forEach {announcement ->
                AnnouncementItem(announcement = announcement,viewModel,userRole,currentTime)
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp))
            }
        }
        if (showAddDialog){
            AddAnnouncementDialog(viewModel = viewModel, onComplete = {
                showAddDialog = false
            }, onDismiss = {
                showAddDialog = false
            })
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(10*1000)
            currentTime = System.currentTimeMillis()
        }
    }

}
