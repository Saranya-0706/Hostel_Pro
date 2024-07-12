package com.example.hostEase.Navigation_App.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostEase.R

@Composable
fun SearchBar(query : String, onQueryChange : (String) -> Unit){

//    OutlinedTextField(
//        value = query,
//        textStyle = TextStyle(
//            fontSize = 15.sp,
//        ),
//        maxLines = 1,
//        placeholder = { Text(text = "Search")},
//        onValueChange = onQueryChange,
//        shape = RoundedCornerShape(25.dp),
//        modifier = Modifier
//            .height(60.dp)
//            .padding(8.dp),
//        colors = OutlinedTextFieldDefaults.colors(),
//
//    )

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(45.dp)
        .clip(RoundedCornerShape(20.dp))
        .border(1.dp, colorResource(id = R.color.SecondaryColor), RoundedCornerShape(20.dp))
        //.padding(5.dp)
            ,
        contentAlignment = Alignment.Center)
    {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            textStyle = TextStyle(
                fontSize = 15.sp
            ),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(horizontal = 10.dp, vertical = 5.dp))


        )
    }
}


@Preview
@Composable
fun searchPrev()
{
    SearchBar(query = "hii") {

    }
}