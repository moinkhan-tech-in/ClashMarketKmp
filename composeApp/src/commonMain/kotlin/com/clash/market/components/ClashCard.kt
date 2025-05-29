package com.clash.market.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clash.market.theme.ClashFont

@Composable
fun ClashCard(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .padding(8.dp)
            .border(1.dp, Color(0xFFE0C97F), RoundedCornerShape(10.dp)), // soft gold border
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFFFDF6E3) // light parchment-style background
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            title?.let {
                Text(
                    text = it,
                    color = Color(0xFF8B6508),
                    fontSize = 16.sp,
                    fontFamily = ClashFont
                )
            }

            subtitle?.let {
                Text(
                    text = it,
                    color = Color(0xFF5C5C5C),
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}