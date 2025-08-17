package com.clash.market.components.widgets

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

sealed class ClashImageSpec {
    data class Res(val res: DrawableResource) : ClashImageSpec()
    data class Url(val url: String?) : ClashImageSpec()
    data class Vector(val vector: ImageVector, val tint: Color = Color.Unspecified) : ClashImageSpec()
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

        is ClashImageSpec.Vector -> {
            Icon(
                imageVector = image.vector,
                contentDescription = null,
                tint = image.tint,
                modifier = modifier
            )
        }
    }
}

fun String.asUrlImageSpec(): ClashImageSpec.Url {
    return ClashImageSpec.Url(this)
}