package com.example.hostEase.Screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import com.example.hostEase.R

fun getHighlightedText(text : String, searchValue : String) : List<Pair<String, Boolean>> {

    if (searchValue.isEmpty()){
        return listOf(Pair(text, false)) //If search is empty return complete text as Non-Matching
    }

    val regex = Regex("\\b$searchValue", RegexOption.IGNORE_CASE)
    val matches  = regex.findAll(text).toList() //Finds all matches and stores in a list

    if (matches.isEmpty())
        return listOf(Pair(text, false)) //For no matches

    val parts = mutableListOf<Pair<String, Boolean>>()
    var lastIndex = 0

    matches.forEach { match ->
        if (match.range.first > lastIndex){
            parts.add(Pair(text.substring(lastIndex, match.range.first), false)) //For segment that comes before the matched string is added as non matching
        }
        parts.add(Pair(match.value, true)) //Matching text is added with matching status as True
        lastIndex = match.range.last + 1 // updating index
    }

    if (lastIndex < text.length){
        parts.add(Pair(text.substring(lastIndex), false)) //Adding the text left after the last match as non matching with status as false
    }

    return parts

}

@Composable
fun HighlightedText(text : String, searchValue: String, modifier : Modifier, fontSize : TextUnit){
    val parts = getHighlightedText(text,searchValue)

    Text(text = buildAnnotatedString {
        parts.forEach { part->
            if (part.second){
                withStyle(style = SpanStyle(
                    fontSize = fontSize,
                    //color = colorResource(id = R.color.SecondaryColor),
                    background = colorResource(id = R.color.LavenderShade)
                ),){
                    append(part.first)
                }
            }else{
                withStyle(style = SpanStyle(fontSize = fontSize))
                { append(part.first) }
            }
        }
    }, modifier = modifier)
}