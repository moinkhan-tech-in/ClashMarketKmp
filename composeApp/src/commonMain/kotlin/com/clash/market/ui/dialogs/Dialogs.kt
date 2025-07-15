package com.clash.market.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.clash.market.components.ClashGlossyButton
import com.clash.market.components.ClashStyleButtonType
import com.clash.market.components.ClashTextField
import com.clash.market.theme.ClashFont

@Composable
fun SingleInputDialog(
    title: String? = null,
    text: String,
    hint: String = "",
    positiveButtonText: String? = null,
    positiveButtonClick: (String) -> Unit = {},
) {

    var tagValue by rememberSaveable { mutableStateOf("") }
    var showDialog by rememberSaveable { mutableStateOf(true) }

    if (showDialog) {
        ClashDialog(
            title = title,
            description = text,
            content = {
                ClashTextField(
                    value = tagValue,
                    hint = hint,
                    onValueChange = { tagValue = it }
                )
            },
            confirmText = positiveButtonText,
            onConfirm = {
                positiveButtonClick(tagValue)
                showDialog = false
            },
            onDismiss = {
                showDialog = false
            }
        )
    }

}

@Composable
fun ClashDialog(
    title: String?,
    description: String?,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    confirmText: String? = null,
    dismissText: String? = null,
    content: @Composable () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFDFD4C2)) // Stone-like color
                .border(3.dp, Color(0xFF4E342E), RoundedCornerShape(16.dp))
//                .shadow(4.dp, RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                title?.let {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                        fontFamily = ClashFont,
                        color = Color(0xFF3E2723) // Dark brown
                    )
                }
                description?.let {
                    Text(
                        text = it,
                        fontSize = 16.sp,
                        fontFamily = ClashFont,
                        color = Color.Black
                    )
                }
                content()
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    dismissText?.let {
                        ClashGlossyButton(
                            type = ClashStyleButtonType.Negative,
                            text = it,
                            onClick = onDismiss
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    confirmText?.let {
                        ClashGlossyButton(
                            type = ClashStyleButtonType.Positive,
                            text = it,
                            onClick = onConfirm
                        )
                    }
                }
            }
        }
    }
}
