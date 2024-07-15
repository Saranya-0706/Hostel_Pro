package com.example.hostEase.Navigation_App.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hostEase.Navigation_App.viewmodel.HomeViewModel
import com.example.hostEase.R
import com.example.hostEase.Screens.BottomNavScreens.LostOrFound.LFCategory
import com.example.hostEase.Screens.BottomNavScreens.LostOrFound.LostFound_ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title : String, showSearchIcon : Boolean,showFilterIcon : Boolean,  drawerState: DrawerState,
           scope : CoroutineScope ,
           searchValueChange : (String) -> Unit,
           filterSelected : (String) -> Unit,
           homeViewModel: HomeViewModel = viewModel()){

    val searchValue by homeViewModel.searchValue.collectAsStateWithLifecycle()
    val isSearchActive by homeViewModel.isSearchActive.collectAsStateWithLifecycle()

    LaunchedEffect(searchValue) {
        Log.d("Top Bar Screen", "Search value : $searchValue")

    }

    LaunchedEffect(isSearchActive) {
        Log.d("TopBar Screen", "isSearchActive : $isSearchActive")
    }
    if (showSearchIcon || showFilterIcon) {
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
                if (showSearchIcon) {
                    IconButton(onClick = {
                        if (isSearchActive) {
                            homeViewModel.deactivateSearch()
                            searchValueChange("")
                        } else {
                            homeViewModel.activateSearch()
                        }
                    })
                    {
                        if (isSearchActive)
                            Icon(imageVector = Icons.Default.Close, contentDescription = "")
                        else
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                }

                if (showFilterIcon){
                   FilterMenu(onSelected = {selectedFilter ->
                       filterSelected(selectedFilter)
                   })
                }
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
@Composable
fun FilterMenu(onSelected : (String) -> Unit,
    homeViewModel: HomeViewModel = viewModel(), lostfoundViewmodel: LostFound_ViewModel = viewModel()){
    var categorySelected by remember { mutableStateOf<LFCategory?>(null) }
    var subCategorySelected by remember { mutableStateOf("") }
    var categoryExpanded by remember { mutableStateOf(false) }
    var subCategoryExpanded by remember { mutableStateOf(false) }

    val isFilterExpanded by homeViewModel.isFilterMenuExpanded.collectAsStateWithLifecycle()
    val categories by lostfoundViewmodel.LFCategories.collectAsStateWithLifecycle()

    LaunchedEffect(isFilterExpanded) {
        categoryExpanded = isFilterExpanded
    }


    Box {
        IconButton(onClick = {
            homeViewModel.onMenuClicked()
        }) {
            Icon(painter = painterResource(id = R.drawable.filter), contentDescription = "Filter")
        }

        DropdownMenu(expanded = categoryExpanded, onDismissRequest = {
            categoryExpanded = false
            homeViewModel.onMenuClicked()
        }) {
            DropdownMenuItem(text = { Text(text = "All Categories")}, onClick = {
                categoryExpanded = false
                onSelected("All")
            })
            categories.forEach {category->
                DropdownMenuItem(text = { Text(text = category.name)}, onClick = {
                    categorySelected = category
                    subCategorySelected = ""
                    categoryExpanded = false
                    subCategoryExpanded = true
                })
            }
            DropdownMenuItem(text = { Text(text = "Others")}, onClick = {
                categorySelected = LFCategory("Others", emptyList())
                categoryExpanded = false
                onSelected(categorySelected!!.name)
            })
        }

        if (categorySelected != null && categorySelected!!.name != "Others"){
            DropdownMenu(expanded = subCategoryExpanded, onDismissRequest = {
                subCategoryExpanded = false
                categoryExpanded = true
            }) {
                DropdownMenuItem(text = { Text(text = "All ${categorySelected!!.name}")}, onClick = {
                    subCategorySelected = categorySelected!!.name
                    onSelected(categorySelected!!.name)
                    //Incase all sub categories within a category is selected, that particular category is passed as filter
                    subCategoryExpanded = false
                 })
                categorySelected!!.subCategories.forEach {subCategory->
                    DropdownMenuItem(text = { Text(text = subCategory)}, onClick = {
                        subCategorySelected = subCategory
                        subCategoryExpanded = false
                        onSelected(subCategorySelected)
                    })
                }
            }
        }

    }
}