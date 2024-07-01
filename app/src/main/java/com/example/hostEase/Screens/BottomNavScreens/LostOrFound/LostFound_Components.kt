package com.example.hostEase.Screens.BottomNavScreens.LostOrFound

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemRow(items : List<LostFoundItem>, viewModel: LostFound_ViewModel,type: String,navController: NavController){

    //val lazyListState = rememberLazyListState()
    //val snapBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

    Row() {
       LazyRow(
           //state = lazyListState,
           //flingBehavior = snapBehavior
       ) {
            items(items){item ->
                Item(item = item, viewModel = viewModel,navController,type)
            }
        }
    }
}

@Composable
fun Item(item :LostFoundItem, viewModel: LostFound_ViewModel,navController: NavController, type:String){

    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    var canDelete by remember {
        mutableStateOf(false)
    }

    canDelete = currentUser?.uid == item.userId

    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(end = 10.dp)
        .width(150.dp)
        .clickable { navController.navigate("itemDetailsScreen/${item.id}/$type") }
    ) {
        com.skydoves.landscapist.glide.GlideImage(
            imageModel = { item.imgUrl },
            imageOptions = ImageOptions(contentScale = ContentScale.Crop),
            modifier = Modifier
                .width(150.dp)
                .weight(1f)
        )

        Row(horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.horizontalScroll(scrollState).weight(0.1f)) {
                Text(text = item.name, fontSize = 18.sp, textAlign = TextAlign.Center)
            }

            if(canDelete) {
                IconButton(onClick = { viewModel.deleteItem(item, type) }, modifier = Modifier.size(38.dp)) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "", modifier = Modifier.size(18.dp))
                }
            }
        }

    }
}

@Composable
fun MatchesFound(viewModel: LostFound_ViewModel){
    Text(text = "Matches Found : ", modifier = Modifier.padding(10.dp).padding(bottom = 0.dp))
}




