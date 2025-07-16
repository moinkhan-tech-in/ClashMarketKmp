package com.clash.market.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_search_character
import com.clash.market.theme.ClashFont
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ClashGuideMessage(
    drawable: DrawableResource,
    message: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.size(32.dp))
        Image(
            painterResource(drawable),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(horizontal = 32.dp),
            text = message,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontFamily = ClashFont,
            style = TextStyle.Default.copy(
                shadow = Shadow(color = Color.White, blurRadius = 10f)
            )
        )
    }
}

@Composable
@Preview
fun ClashGuideMessagePreview() {
    ClashGuideMessage(
        drawable = Res.drawable.ic_search_character,
        message = "Chief, Start searching for a clan."
    )
}