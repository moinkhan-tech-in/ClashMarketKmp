package com.clash.market.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = .5f), RoundedCornerShape(12.dp)), // Dark brown border
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontFamily = ClashFont
        ),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
        )
    )
}