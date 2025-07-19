package com.clash.market.components.clash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_link_village
import com.clash.market.components.ClashGlossyButton
import org.jetbrains.compose.resources.painterResource

@Composable
fun ClashLinkVillageMessage(
    onLinkClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterVertically)
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(Res.drawable.ic_link_village),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(horizontal = 32.dp),
            textAlign = TextAlign.Center,
            lineHeight = 18.sp,
            text = "Link your village to view your profile, clan wars and more."
        )
        Spacer(Modifier.size(8.dp))
        ClashGlossyButton(
            text = "Link My Village!",
            onClick = onLinkClick
        )
    }
}