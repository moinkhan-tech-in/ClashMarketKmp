package com.clash.market.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clash.market.theme.ClashFont

@Composable
fun ClashTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    leadingIcon: ImageVector? = null,
    modifier: Modifier = Modifier
) {
    val leadingIconComposable = leadingIcon?.let {
        @Composable {
            Icon(imageVector = leadingIcon, contentDescription = null)
        }
    }

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
        leadingIcon = leadingIconComposable,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFDF6E3)) // Pale stone-like background
            .border(2.dp, Color(0xFFE0C97F), RoundedCornerShape(12.dp)), // Dark brown border
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