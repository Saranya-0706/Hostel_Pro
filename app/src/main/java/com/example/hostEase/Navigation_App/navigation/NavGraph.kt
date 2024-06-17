package com.example.hostEase.Navigation_App.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hostEase.Screens.BottomNavScreens.ChatScreen
import com.example.hostEase.Screens.BottomNavScreens.ComplaintScreen
import com.example.hostEase.Screens.BottomNavScreens.GeneralScreen
import com.example.hostEase.Screens.BottomNavScreens.LostFoundScreen
import com.example.hostEase.Screens.NavDrawerScreens.ProfileScreen
import com.example.hostEase.Screens.NavDrawerScreens.a
import com.example.hostEase.Screens.NavDrawerScreens.b
import com.example.hostEase.Screens.NavDrawerScreens.c
import com.example.hostEase.UserViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph(navController: NavHostController, userViewModel: UserViewModel = viewModel()){
    NavHost(navController = navController, startDestination = BottomNavItems.General.route){

        composable(BottomNavItems.General.route){ GeneralScreen() }
        composable(BottomNavItems.Complaints.route){ ComplaintScreen() }
        composable(BottomNavItems.LostFound.route){ LostFoundScreen() }
        composable(BottomNavItems.Chat.route){ ChatScreen() }
        composable(NavDrawerItems.Profile.route){ ProfileScreen(userViewModel, FirebaseAuth.getInstance().currentUser!!.uid) }
        composable(NavDrawerItems.a.route){ a() }
        composable(NavDrawerItems.b.route){ b() }
        composable(NavDrawerItems.c.route){ c() }

    }
}

