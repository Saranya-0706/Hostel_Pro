package com.example.hostEase.Screens.BottomNavScreens.LostOrFound

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.skydoves.landscapist.ImageOptions

@Composable
fun LostFoundScreen(filterSelected : String, viewModel: LostFound_ViewModel,navController: NavController
) {
    val lostItems by viewModel.lostItems.collectAsStateWithLifecycle()
    val foundItems by viewModel.foundItems.collectAsStateWithLifecycle()

    val filteredLostItems by viewModel.filteredLostItems.collectAsStateWithLifecycle()
    val filteredFoundItems by viewModel.filteredFoundItems.collectAsState()

    LaunchedEffect(filterSelected) {
       viewModel.filterLostFound(filterSelected)
    }

    Column(modifier = Modifier
        .fillMaxSize()) {
        //MatchesFound(viewModel = viewModel)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(10.dp).padding(top = 0.dp)
        ) {

            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Lost Items", fontSize = 20.sp)
                    FloatingActionButton(onClick = { navController.navigate("addItem/lost") },
                        modifier = Modifier.size(40.dp), shape = CircleShape) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "AddLostItem", modifier = Modifier.size(22.dp))
                    }
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                ){
                    ItemRow(items =
                    if(filterSelected.isNotEmpty()|| filteredLostItems.isNotEmpty() || filteredFoundItems.isNotEmpty())
                        filteredLostItems else lostItems,
                     viewModel = viewModel, type = "lost", navController = navController)
                }

            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(12.dp))

            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Found Items", fontSize = 20.sp, modifier = Modifier)
                    FloatingActionButton(onClick = { navController.navigate("addItem/found") },
                        modifier = Modifier.size(40.dp), shape = CircleShape) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "AddFoundItem", modifier = Modifier.size(22.dp))
                    }
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    ItemRow(
                        items = if(filterSelected.isNotEmpty() || filteredLostItems.isNotEmpty() || filteredFoundItems.isNotEmpty())
                            filteredFoundItems else foundItems,
                        viewModel = viewModel,
                        type = "found",
                        navController = navController
                    )
                }
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItem(viewModel: LostFound_ViewModel, type : String, navController: NavController){
    var name by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var itemDetails by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var categorySelected by remember { mutableStateOf("") }
    var subCategorySelected by remember { mutableStateOf("Other") }
    var imgUri by remember { mutableStateOf<Uri?>(null) }
    var categoryExpanded by remember { mutableStateOf(false) }
    var subCategoryExpanded by remember { mutableStateOf(false) }

    val categories by viewModel.LFCategories.collectAsStateWithLifecycle()

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri->
        imgUri = uri
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(15.dp)
        .padding(top = 0.dp)) {

        Text(text = "Add $type Item", fontSize = 22.sp, textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp))

        ExposedDropdownMenuBox(
            expanded = categoryExpanded,
            onExpandedChange = { categoryExpanded = it }) {
            OutlinedTextField(value = categorySelected, onValueChange = {}, readOnly = true,
                label = { Text(text = "Select Category ") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = categoryExpanded,
                onDismissRequest = { categoryExpanded = false}) {
                categories.forEach {category->
                    DropdownMenuItem(text = { Text(text = category.name) }, onClick = {
                        categorySelected = category.name
                        subCategorySelected = ""
                        categoryExpanded = false
                    })
                }
                DropdownMenuItem(text = { Text(text = "Other") }, onClick = {
                    categorySelected = "Others"
                    subCategorySelected = "Other"
                    categoryExpanded = false
                })
            }
        }

        val subCategories = categories.find {
            it.name == categorySelected
        }?.subCategories ?: emptyList()

        if (subCategories.isNotEmpty() && categorySelected  != "Other") {
            ExposedDropdownMenuBox(
                expanded = subCategoryExpanded,
                onExpandedChange = { subCategoryExpanded = it }) {
                OutlinedTextField(value = subCategorySelected, onValueChange = {}, readOnly = true,
                    label = { Text(text = "Select Sub Category ") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = subCategoryExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = subCategoryExpanded,
                    onDismissRequest = { subCategoryExpanded = false}) {
                    subCategories.forEach {subCategory->
                        DropdownMenuItem(text = { Text(text = subCategory) }, onClick = {
                            subCategorySelected = subCategory
                            subCategoryExpanded = false
                        })
                    }
                }
            }
        }

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text(text = "Item Name")}, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = model, onValueChange = { model = it }, label = { Text(text = "Item Model")},modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = itemDetails, onValueChange = { itemDetails = it }, label = { Text(text = "Details")},modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = contact, onValueChange = { contact = it }, label = { Text(text = "Contact Details")},modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(10.dp))

        imgUri?.let {
            com.skydoves.landscapist.glide.GlideImage(
                imageModel = { it },
                imageOptions = ImageOptions(contentScale = ContentScale.Fit),
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally))
        }

        Spacer(modifier = Modifier.height(10.dp))


        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){

            Button(onClick = {
                launcher.launch("image/*")
            }
            ) {
                Text(text = "Select Image")
            }

            Button(onClick = {
                imgUri.let { uri->
                    if (uri != null) {
                        viewModel.uploadImage(uri,type, onSuccess = {imgUrl->
                            val item = LostFoundItem(
                                name = name,
                                model = model,
                                itemDetails = itemDetails,
                                contactDetails = contact,
                                category = if(categorySelected.isNotEmpty()) categorySelected else "Others",
                                subCategory = subCategorySelected,
                                imgUrl = imgUrl
                            )
                            viewModel.addItem(item,type)
                            navController.popBackStack()
                        },
                            onFailure = {

                            })
                    }else{
                        val item = LostFoundItem(
                            name = name,
                            model = model,
                            itemDetails = itemDetails,
                            category = if(categorySelected.isNotEmpty()) categorySelected else "Others",
                            subCategory = subCategorySelected,
                            contactDetails = contact)
                        viewModel.addItem(item,type)
                        navController.popBackStack()
                    }

                }
            }) {
                Text(text = "Submit")
            }
        }

    }
}









