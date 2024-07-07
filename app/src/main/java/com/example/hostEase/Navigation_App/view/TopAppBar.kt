package com.example.hostEase.Navigation_App.view

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.hostEase.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title : String, drawerState: DrawerState, scope : CoroutineScope , openSearch : () -> Unit, openMenu : () -> Unit){
    TopAppBar(
        title = { Text(text = title)},
        //colors = TopAppBarColors,
        modifier = Modifier.background(colorResource(id = R.color.SecondaryColor)),
        navigationIcon = {
            IconButton(onClick = {
                scope.launch { drawerState.open() }

            }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription ="" )
            }
        },
        actions = {
            IconButton(onClick = openSearch) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
            IconButton(onClick = openMenu) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
            }
        }
    )
}