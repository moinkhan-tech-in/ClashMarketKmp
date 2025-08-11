package com.clash.market.utils

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.pow
import kotlin.math.round

enum class ArrowSide { Left, Right }

fun calloutRectWithArrow(
    arrowSide: ArrowSide = ArrowSide.Right,
    arrowWidth: Dp = 10.dp,
    arrowHeight: Dp = 12.dp,
    arrowCenterYFraction: Float = 0.5f
): Shape = object : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: Density
    ): Outline {
        val aw = with(density) { arrowWidth.toPx() }
        val ah = with(density) { arrowHeight.toPx() }
        val cy = (size.height * arrowCenterYFraction).coerceIn(0f, size.height)
        val y0 = (cy - ah / 2).coerceAtLeast(0f)
        val y1 = (cy + ah / 2).coerceAtMost(size.height)

        val path = Path()
        if (arrowSide == ArrowSide.Right) {
            val bodyRight = (size.width - aw).coerceAtLeast(0f)
            path.addRect(Rect(0f, 0f, bodyRight, size.height))
            path.moveTo(bodyRight, y0)
            path.lineTo(size.width, cy)
            path.lineTo(bodyRight, y1)
            path.close()
        } else {
            val bodyLeft = aw.coerceAtMost(size.width)
            path.addRect(Rect(bodyLeft, 0f, size.width, size.height))
            path.moveTo(bodyLeft, y0)
            path.lineTo(0f, cy)
            path.lineTo(bodyLeft, y1)
            path.close()
        }
        return Outline.Generic(path)
    }
}


fun Double?.formatPercent(decimals: Int = 2): String {
    if (this == null) return "0%"
    val factor = 10.0.pow(decimals)
    val rounded = round(this * factor) / factor
    return "${rounded}%"
}