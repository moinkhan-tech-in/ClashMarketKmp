package com.clash.market.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@androidx.compose.runtime.Composable
fun ClashSearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String = "",
    onValueChange: (String) -> Unit,
    onQuerySubmit: (String) -> Unit = {},
    btnEnabled: () -> Boolean = { true },
    btnText: String? = null,
) {
    Box(modifier) {
        ClashTextField(
            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
            leadingIcon = Icons.Default.Search,
            value = value,
            hint = hint,
            onValueChange = onValueChange
        )
        btnText?.let {
            ClashGlossyButton(
                type = ClashStyleButtonType.Positive,
                modifier = Modifier.align(Alignment.CenterEnd).padding(4.dp),
                enabled = btnEnabled.invoke(),
                text = it,
                onClick = { onQuerySubmit(value) }
            )
        }
    }
}

@androidx.compose.runtime.Composable
fun ClashSearchTextFieldValue(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    hint: String = "",
    onValueChange: (TextFieldValue) -> Unit,
    onQuerySubmit: (TextFieldValue) -> Unit = {},
    btnEnabled: () -> Boolean = { true },
    btnText: String? = null,
) {
    Box(modifier) {
        ClashTextFieldValue(
            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
            leadingIcon = Icons.Default.Search,
            value = value,
            hint = hint,
            onValueChange = onValueChange
        )
        btnText?.let {
            ClashGlossyButton(
                type = ClashStyleButtonType.Positive,
                modifier = Modifier.align(Alignment.CenterEnd).padding(4.dp),
                enabled = btnEnabled.invoke(),
                text = it,
                onClick = { onQuerySubmit(value) }
            )
        }
    }
}
