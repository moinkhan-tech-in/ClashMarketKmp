package com.clash.market.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clash.market.theme.ClashFont

@Composable
fun ClashStyleTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = hint,
                color = Color.Gray,
                fontFamily = ClashFont
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFDFD4C2)) // Pale stone-like background
            .border(2.dp, Color(0xFF4E342E), RoundedCornerShape(12.dp)), // Dark brown border
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 18.sp,
            fontFamily = ClashFont
        ),
        singleLine = true,
        colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color(0xFF4E342E)
        )
    )
}