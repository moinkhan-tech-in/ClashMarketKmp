package com.clash.market.components.clash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clash.market.components.ClashGlossyButton
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ClashMessageInfo(
    icon: DrawableResource? = null,
    iconPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    message: String? = null,
    btnText: String? = null,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterVertically)
    ) {
        icon?.let {
            Image(
                modifier = Modifier.fillMaxWidth().padding(iconPadding),
                painter = painterResource(it),
                contentDescription = null
            )
        }
        message?.let {
            Text(
                modifier = Modifier.padding(horizontal = 32.dp),
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                text = it
            )
        }
        Spacer(Modifier.size(8.dp))
        btnText?.let {
            ClashGlossyButton(
                text = it,
                onClick = { onClick?.invoke() }
            )
        }
    }
}