package com.example.hostEase

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

@Composable
fun GlideImage(context : Context, imgUrl: Any, modifier: Modifier = Modifier ){

    val imageBitmap = remember {
        mutableStateOf<ImageBitmap?>(null)
    }

    Glide.with(context)
        .asBitmap()
        .load(imgUrl)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                imageBitmap.value = resource.asImageBitmap()
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }

        })

    imageBitmap.value?.let {
        Image(bitmap = it, contentDescription = "", modifier = modifier)
    }

}