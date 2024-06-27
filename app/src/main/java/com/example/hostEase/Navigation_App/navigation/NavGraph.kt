package com.example.hostEase.Navigation_App.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hostEase.Screens.BottomNavScreens.ChatScreen
import com.example.hostEase.Screens.BottomNavScreens.Complaints.ComplaintScreen
import com.example.hostEase.Screens.BottomNavScreens.Announcements.GeneralScreen
import com.example.hostEase.Screens.BottomNavScreens.LostFoundScreen
import com.example.hostEase.Screens.NavDrawerScreens.Profile.ProfileScreen
import com.example.hostEase.Screens.NavDrawerScreens.PrivateComplaints.PrivateComplaints
import com.example.hostEase.Screens.NavDrawerScreens.c
import com.example.hostEase.Screens.NavDrawerScreens.Profile.UserViewModel
import com.example.hostEase.Screens.NavDrawerScreens.Settings.Settings
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph(navController: NavHostController, userViewModel: UserViewModel = viewModel()){
    NavHost(navController = navController, startDestination = BottomNavItems.General.route){

        composable(BottomNavItems.General.route){ GeneralScreen() }
        composable(BottomNavItems.Complaints.route){ ComplaintScreen() }
        composable(BottomNavItems.LostFound.route){ LostFoundScreen() }
        composable(BottomNavItems.Chat.route){ ChatScreen() }
        composable(NavDrawerItems.Profile.route){ ProfileScreen(userViewModel, FirebaseAuth.getInstance().currentUser!!.uid) }
        composable(NavDrawerItems.Settings.route){ Settings() }
        composable(NavDrawerItems.PrivateComplaints.route){ PrivateComplaints() }
        composable(NavDrawerItems.c.route){ c() }

    }
}

