package com.hav.iot.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hav.iot.R
import com.hav.iot.ui.theme.SecondColor
import com.hav.iot.ui.theme.ThirdColor

@Composable
fun TextHeader(text: String) {
    val font = FontFamily(Font(R.font.notosans))
    Box(
        modifier = Modifier
            .padding(5.dp)
            .background(color = SecondColor)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(5.dp),
            style = TextStyle(
                fontSize = 26.sp,
                fontWeight = FontWeight(1000),
                fontFamily = font
            ))
    }
}

@Composable
fun TextHeader2(text: String) {
    val font = FontFamily(Font(R.font.notosans))
    Box(
        modifier = Modifier
            .padding(5.dp)
            .background(color = SecondColor)
            .fillMaxWidth()
    ) {
        Row{
            Text(
                text = text,
                modifier = Modifier.padding(5.dp),
                color = ThirdColor,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight(1000),
                    fontFamily = font
                ))
            Spacer(modifier = Modifier.weight(1f))
            Icon(painter = painterResource(id = R.drawable.ic_chart), contentDescription = null)
        }
    }
}

@Composable
fun MainButton(text: String, onClick: () -> Unit, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = color)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.notosans)),
                color = Color.Black,
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    searchQuery: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = searchQuery,
        onValueChange = { it ->
            onQueryChanged(it)
        } ,
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .indicatorLine(
                enabled = false,
                isError = false,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                focusedIndicatorLineThickness = 1.dp,
                unfocusedIndicatorLineThickness = 0.dp
            ),
        placeholder = { androidx.compose.material3.Text(text = "Search") },
        leadingIcon = {
            androidx.compose.material3.Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = ""
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedContainerColor = Color.White,
            unfocusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color.Black,
        )
    )
}

