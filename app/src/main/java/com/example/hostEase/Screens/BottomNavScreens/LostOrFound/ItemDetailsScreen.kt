package com.example.hostEase.Screens.BottomNavScreens.LostOrFound

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
fun itemDetailsScreen(viewModel: LostFound_ViewModel,type : String, itemId : String?, navController: NavController){

    val item by viewModel.item.collectAsStateWithLifecycle()

    LaunchedEffect(itemId) {
       if (itemId != null )
           viewModel.getItemByID(itemId,type)
    }

    item?.let {item->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .padding(top = 0.dp)
        ) {

            Text(text = item.name, fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f), contentAlignment = Alignment.Center)
            {
                com.skydoves.landscapist.glide.GlideImage(
                    imageModel = { item.imgUrl },
                    imageOptions = ImageOptions(contentScale = ContentScale.Fit),
                    //previewPlaceholder =
                )
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(15.dp))

            if (item.model.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp)
                ) {
                    Text(text = " Model   :    ", fontSize = 18.sp)
                    Text(text = item.model, fontSize = 18.sp)
                }
            }

            if (item.itemDetails.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.Start, modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp)
                ) {
                    Text(text = " Details   :   ", fontSize = 18.sp)
                    Text(text = item.itemDetails, fontSize = 18.sp)
                }
            }

            if (item.contactDetails.isNotEmpty()) {
                Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                    Text(text = " Contact  :  ", fontSize = 18.sp)
                    Text(text = item.contactDetails, fontSize = 18.sp)
                }
            }
        }
    }
}