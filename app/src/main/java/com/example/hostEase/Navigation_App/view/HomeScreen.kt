package com.example.hostEase.Navigation_App.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.hostEase.Navigation_App.navigation.BottomNavItems
import com.example.hostEase.Navigation_App.navigation.NavGraph
import com.example.hostEase.Navigation_App.viewmodel.HomeViewModel
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

        var currentRoute by remember {
            mutableStateOf("home")
        }

        var searchValue by remember {
            mutableStateOf("")
        }

        val items = homeViewModel.navDrawerItems

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    NavDrawer(navController = navController,
                        drawerState = drawerState,
                        scope = scope,
                        items = items,
                        onTabSelected = {route->
                            currentRoute = route
                            searchValue = ""
                            homeViewModel.deactivateSearch()
                        })
                }
            }
        ) {
            Scaffold (
                topBar = { TopBar(title = "HostEase", drawerState = drawerState, scope = scope,
                    showSearchIcon = shouldShowSearch(currentRoute),
                    showMenuIcon = shouldShowMenu(currentRoute),
                    searchValueChange = {
                        searchValue = it
                    }
                ) },
                bottomBar = {
                    BottomNavBar(navController = navController, onTabSelected = {route->
                        homeViewModel.deactivateSearch()
                        searchValue = ""
                        currentRoute = route
                    })
                }
            ){
                val padding = it
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(padding)
                ){
                    NavGraph(searchValue, navController = navController)
                }

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

fun shouldShowSearch(route : String?) :  Boolean{
    return route in listOf("home", "complaint", "PrivateComplaints")
}

fun shouldShowMenu(route : String?) :  Boolean{
    return route in listOf("lostFound")
}