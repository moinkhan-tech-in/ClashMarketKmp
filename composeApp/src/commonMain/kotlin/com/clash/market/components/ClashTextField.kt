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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

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
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color.Gray
                )
            )
        },
        leadingIcon = leadingIconComposable,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = .5f),
                shape = RoundedCornerShape(12.dp)
            ),
        textStyle = MaterialTheme.typography.labelLarge.copy(
            color = Color.Black
        ),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
        )
    )
}

@Composable
fun ClashTextFieldValue(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
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
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color.Gray
                )
            )
        },
        leadingIcon = leadingIconComposable,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = .5f),
                shape = RoundedCornerShape(12.dp)
            ),
        textStyle = MaterialTheme.typography.labelLarge.copy(
            color = Color.Black
        ),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
        )
    )
}