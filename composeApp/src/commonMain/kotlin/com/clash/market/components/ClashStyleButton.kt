package com.clash.market.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clash.market.theme.ClashFont
import org.jetbrains.compose.ui.tooling.preview.Preview


val NegativeGradient = listOf(
    Color(0xFFFF6A6A),
    Color(0xFFB71C1C)
)

val PositiveGradient = listOf(
    Color(0xFFA3E44D),
    Color(0xFF388E3C)
)

val DisabledGradient = listOf(
    Color(0xFFB0B0B0),
    Color(0xFF888888)
)

enum class ClashStyleButtonType {
    Positive,
    Negative;

    fun gradient(enabled: Boolean = true): List<Color> {
        return when  {
            enabled == false -> DisabledGradient
            this == Positive -> PositiveGradient
            this == Negative -> NegativeGradient
            else -> listOf()
        }
    }
}


@Composable
fun ClashGlossyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    type: ClashStyleButtonType = ClashStyleButtonType.Positive,
    rightIcon: ImageVector? = null,
    enabled: Boolean = true,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(enabled = enabled) { onClick() }
            .background(
                brush = Brush.verticalGradient(type.gradient(enabled)),
                shape = RoundedCornerShape(12.dp)
            )
            .border(1.5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
            .padding(vertical = 4.dp)
            .height(30.dp)
    ) {
        // Glass highlight
//        Box(
//            modifier = Modifier
//                .matchParentSize()
//                .padding(vertical = 2.dp, horizontal = 8.dp)
//                .heightIn(18.dp)
//                .clip(RoundedCornerShape(8.dp))
//                .background(
//                    Brush.verticalGradient(
//                        colors = listOf(
//                            Color.White.copy(alpha = 0.35f),
//                            Color.White.copy(alpha = 0.35f)
//                        )
//                    )
//                )
//        )

        // Content
        Row(
            modifier = Modifier.align(Alignment.Center)
                .padding(horizontal = 28.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                fontFamily = ClashFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White,
                style = TextStyle.Default.copy(
                    shadow = Shadow(Color.Black, offset = Offset(1f, 1f), blurRadius = 2f)
                )
            )

            rightIcon?.let {
                Icon(
                    modifier = Modifier.padding(start = 8.dp),
                    imageVector = it,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
@Preview
fun ClashStyleButtonPreview() {
    ClashGlossyButton(text = "Test", onClick = {})
}