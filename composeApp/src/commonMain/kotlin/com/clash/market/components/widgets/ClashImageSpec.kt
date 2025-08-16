package com.clash.market.components.widgets

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

sealed class ClashImageSpec {
    data class Res(val res: DrawableResource) : ClashImageSpec()
    data class Url(val url: String?) : ClashImageSpec()
}

@Composable
fun ClashImage(modifier: Modifier = Modifier, image: ClashImageSpec) {
    when (image) {
        is ClashImageSpec.Res -> {
            Image(
                painter = painterResource(resource = image.res),
                contentDescription = null,
                modifier = modifier
            )
        }

        is ClashImageSpec.Url -> AsyncImage(
            model = image.url,
            contentDescription = null,
            modifier = modifier
        )
    }
}

fun String.asUrlImageSpec(): ClashImageSpec.Url {
    return ClashImageSpec.Url(this)
}