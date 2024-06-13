package com.example.hostEase.Navigation_App.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.hostEase.Navigation_App.navigation.BottomNavItems
import com.example.hostEase.Navigation_App.viewmodel.HomeViewModel
import com.example.hostEase.Navigation_App.navigation.NavGraph
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        val navController = rememberNavController()
        val scope = rememberCoroutineScope()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

        val items = homeViewModel.navDrawerItems

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    NavDrawer(navController = navController,
                        drawerState = drawerState,
                        scope = scope,
                        items = items)
                }
            }
        ) {

            Scaffold (
                topBar = { TopBar(title = "HostEase", drawerState = drawerState, scope = scope) },
                bottomBar = {
                    BottomNavBar(navController = navController)
                }
            ){
                val padding = it
                NavGraph(navController = navController)
            }

            BackHandler(enabled = drawerState.isOpen) {
                scope.launch {
                    drawerState.close()
                }
                if(navController.currentDestination?.route != BottomNavItems.General.route)
                    navController.navigate(BottomNavItems.General.route)
            }
        }
    }
}