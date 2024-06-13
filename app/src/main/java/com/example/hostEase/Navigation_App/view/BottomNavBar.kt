package com.example.hostEase.Navigation_App.view

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.hostEase.Navigation_App.navigation.BottomNavItems
import com.example.hostEase.R

@Composable
fun BottomNavBar(navController : NavController){
    val items = listOf(
        BottomNavItems.General,
        BottomNavItems.Complaints,
        BottomNavItems.LostFound,
        BottomNavItems.Chat
    )

    NavigationBar(
        containerColor = colorResource(id = R.color.SecondaryColor),
        contentColor = Color.Black

    ) {

        val currentRouteAsState = navController.currentBackStackEntryAsState().value?.destination?.route
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        items.forEach { bottomNavItem ->
            NavigationBarItem(
                selected = bottomNavItem.route == currentRouteAsState,
                onClick = {
                    navController.navigate(bottomNavItem.route){

                        popUpTo(BottomNavItems.General.route)
                       /* if(currentRouteAsState == BottomNavItems.General.route){
                            popUpTo(0){
                            saveState = true}
                        }
                        else
                            popUpTo(BottomNavItems.General.route)

                        */
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = bottomNavItem.icon),
                        contentDescription = bottomNavItem.name
                    )
                },
                label = { Text(bottomNavItem.name) }
            )

        }
    }
}