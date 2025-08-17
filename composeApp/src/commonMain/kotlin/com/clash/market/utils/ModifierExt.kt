package com.clash.market.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sin

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
    var str = rounded.toString()
    if (str.contains(".")) {
        str = str.trimEnd('0').trimEnd('.') // remove useless .0 or trailing zeros
    }
    return "$str%"
}


fun Modifier.glowingBorder(
    color: Color,
    borderWidth: Dp = 2.dp,
    cornerRadius: Dp = 12.dp,
    glowWidth: Dp = 8.dp,      // how thick the glow can extend
    layers: Int = 6,            // more layers = smoother glow
    durationMillis: Int = 1200  // pulse speed
): Modifier = composed {
    val density = LocalDensity.current
    val strokePx = with(density) { borderWidth.toPx() }
    val radiusPx = with(density) { cornerRadius.toPx() }
    val glowPx   = with(density) { glowWidth.toPx() }

    val infinite = rememberInfiniteTransition(label = "glow")
    val phase by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )
    // 0.5..1.0 pulsation
    val pulse = 0.5f + 0.5f * sin(phase * 2f * PI).toFloat()

    drawBehind {
        val half = strokePx / 2f
        val topLeft = Offset(half, half)
        val sizeRect = Size(size.width - strokePx, size.height - strokePx)

        // Glow layers (outside -> inside)
        for (i in 1..layers) {
            val t = i / layers.toFloat()
            val width = strokePx + t * glowPx * pulse
            val alpha = (1f - t) * 0.55f * pulse
            drawRoundRect(
                color = color.copy(alpha = alpha),
                topLeft = topLeft,
                size = sizeRect,
                cornerRadius = CornerRadius(radiusPx, radiusPx),
                style = Stroke(width)
            )
        }

        // Solid border on top
        drawRoundRect(
            color = color,
            topLeft = topLeft,
            size = sizeRect,
            cornerRadius = CornerRadius(radiusPx, radiusPx),
            style = Stroke(strokePx)
        )
    }
}


fun Modifier.animatedCircularGradientBorder(
    borderWidth: Dp = 4.dp,
    colors: List<Color> = listOf(Color(0xFFFFD700), Color(0xFFFF8C00), Color(0xFFFFD700)), // gold sweep
    durationMillis: Int = 1800,
    trackColor: Color = Color.Transparent // set e.g. Color.White.copy(alpha = .15f) for a subtle static ring
): Modifier = composed {
    val strokePx = with(LocalDensity.current) { borderWidth.toPx() }
    val rot by rememberInfiniteTransition(label = "circ-grad")
        .animateFloat(
            initialValue = 0f, targetValue = 360f,
            animationSpec = infiniteRepeatable(tween(durationMillis, easing = LinearEasing)),
            label = "deg"
        )

    drawBehind {
        val d = min(size.width, size.height) - strokePx
        val left = (size.width - d) / 2f
        val top = (size.height - d) / 2f
        val rect = Rect(left, top, left + d, top + d)
        val center = Offset(size.width / 2f, size.height / 2f)

        if (trackColor.alpha > 0f) {
            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = rect.topLeft,
                size = rect.size,
                style = Stroke(width = strokePx)
            )
        }

        rotate(degrees = rot, pivot = center) {
            drawArc(
                brush = Brush.sweepGradient(colors = colors, center = center),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = rect.topLeft,
                size = rect.size,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
        }
    }
}