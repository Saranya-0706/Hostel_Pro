package com.example.hostEase.Screens.NavDrawerScreens.Profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.hostEase.R

@Composable
fun GlideImage(imgUrl: Any, modifier: Modifier = Modifier ){

    val context= LocalContext.current
    val imageBitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    LaunchedEffect(imgUrl) {
        Glide.with(context)
            .asBitmap()
            .load(imgUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    imageBitmap.value = resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })

    }


    imageBitmap.value?.let {bmp->
        Image(bitmap = bmp.asImageBitmap(), contentDescription = "", modifier = modifier, contentScale = ContentScale.Crop)
    }
}