package com.example.hostEase.Navigation_App.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hostEase.Navigation_App.viewmodel.HomeViewModel
import com.example.hostEase.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title : String, showSearchIcon : Boolean,  drawerState: DrawerState,
           scope : CoroutineScope ,
           searchValueChange : (String) -> Unit,
           homeViewModel: HomeViewModel = viewModel()){

    val searchValue by homeViewModel.searchValue.collectAsStateWithLifecycle()
    val isSearchActive by homeViewModel.isSearchActive.collectAsStateWithLifecycle()

    LaunchedEffect(searchValue) {
        Log.d("Top Bar Screen", "Search value : $searchValue")

    }

    LaunchedEffect(isSearchActive) {
        Log.d("TopBar Screen", "isSearchActive : $isSearchActive")
    }
    if (showSearchIcon) {
        TopAppBar(
            title = {
                if (isSearchActive) {
                    SearchBar(query = searchValue) { value ->
                        searchValueChange(value)
                       homeViewModel.updateSearchValue(value)
                    }
                } else {
                    Text(text = title)
                }
            },
            //colors = TopAppBarColors,
            modifier = Modifier.background(colorResource(id = R.color.SecondaryColor)),
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch { drawerState.open() }

                }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "")
                }
            },
            actions = {

                IconButton(onClick = {
                    if(isSearchActive) {
                        homeViewModel.deactivateSearch()
                        searchValueChange("")
                    }
                    else {
                        homeViewModel.activateSearch()
                    }
                })
                {
                    if (isSearchActive)
                        Icon(imageVector = Icons.Default.Close, contentDescription = "")
                    else
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
//            IconButton(onClick = ) {
//                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
//            }
            }
        )
    }else
    {
        TopAppBar(
            title = { Text(text = title) },
            modifier = Modifier.background(colorResource(id = R.color.SecondaryColor)),
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch { drawerState.open() }

                }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "")
                }
            })
    }
}