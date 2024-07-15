package com.example.hostEase.Screens.BottomNavScreens.LostOrFound

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun LostFoundNavGraph(filterSelected : String,
    viewModel: LostFound_ViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "lostfoundScreen") {

        composable("lostfoundScreen") { LostFoundScreen(filterSelected,viewModel,navController)}

        composable("addItem/{type}"){backStackEntry->
            val type = backStackEntry.arguments?.getString("type")
            AddItem(viewModel = viewModel, type = type.toString(), navController = navController )
        }

        composable("itemDetailsScreen/{itemId}/{type}") {backStackEntry->
            val id = backStackEntry.arguments?.getString("itemId")
            val type = backStackEntry.arguments?.getString("type")
            itemDetailsScreen(viewModel = viewModel, type = type.toString() , itemId = id, navController =navController )
        }
    }
}









