package com.example.hostEase.Navigation_App.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.hostEase.Navigation_App.navigation.BottomNavItems
import com.example.hostEase.Navigation_App.navigation.NavDrawerItems
import com.example.hostEase.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavDrawer(navController: NavController, drawerState: DrawerState, scope : CoroutineScope, items : List<NavDrawerItems>){

    Box(modifier = Modifier
        .background(colorResource(id = R.color.primaryColor))
        .fillMaxWidth()
        .height(180.dp))
    {
        Text(text = "")
    }

    Divider()

    Column {
        val currentRouteAsState = navController.currentBackStackEntryAsState().value?.destination?.route
        //val coroutineScope = rememberCoroutineScope()
        items.forEach{navItem ->

            NavigationDrawerItem(
                label = { Text(text = navItem.name)},
                selected = navItem.route == currentRouteAsState,
                onClick = {
                    scope.launch {
                        drawerState.close()
                    }
                    navController.navigate(navItem.route){
                        popUpTo(BottomNavItems.General.route){
                            saveState = true}
                        //launchSingleTop = true
                        //restoreState = true
                    }
                })
        }
    }
}