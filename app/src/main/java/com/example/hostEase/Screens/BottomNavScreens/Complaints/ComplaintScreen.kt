package com.example.hostEase.Screens.BottomNavScreens.Complaints

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hostEase.Screens.NavDrawerScreens.Profile.UserViewModel
import kotlinx.coroutines.delay

@Composable
fun ComplaintScreen(searchValue : String, complaintViewModel: ComplaintViewModel = viewModel(), userViewModel: UserViewModel = viewModel()) {

    val user by userViewModel.user.observeAsState()
    val allComplaints by complaintViewModel.complaints.collectAsStateWithLifecycle()
    val filteredComplaints by complaintViewModel.searchMatchedComplaints.collectAsStateWithLifecycle()
    var complaints : List<Complaint>
    val currentUser by complaintViewModel.currentUser.collectAsStateWithLifecycle()
    val loading by complaintViewModel.loading.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    var userRole by remember { mutableStateOf("Student") }
    var userHostel by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }
    var currentTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    LaunchedEffect(Unit) {
        userViewModel.loadUserProfile()
        complaintViewModel.loadComplaint()
    }
    userRole = user?.role ?: "Student"
    userHostel = user?.hostel ?: ""

    LaunchedEffect(searchValue) {
        complaintViewModel.loadSearchedComplaints(searchValue)
    }

    Scaffold (
       floatingActionButton = {
            /*Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd){
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    */if (userRole == "Student" ){
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Complaint")
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

            if (searchValue.isNotEmpty()) {
                complaints = filteredComplaints
            }else{
                complaints = allComplaints
            }

            complaints.forEach { complaint ->
                if (complaint.hostel == userHostel) {
                    if (complaint.type == "Public") {
                        ComplaintsItem(
                            searchValue = searchValue,
                            complaint = complaint,
                            viewModel = complaintViewModel,
                            userRole = userRole,
                            currentTime = currentTime,
                            canDelete = currentUser?.uid == complaint.userId,
                            onUpvote = { complaintViewModel.upVoteComplaint(complaint.id) },
                            onDownvote = { complaintViewModel.downVoteComplaint(complaint.id) },
                            currentUser = currentUser
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                        )
                    }
                }
            }

        }
        if (showAddDialog){
            AddComplaintDialog(viewModel = complaintViewModel, onComplete = {
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