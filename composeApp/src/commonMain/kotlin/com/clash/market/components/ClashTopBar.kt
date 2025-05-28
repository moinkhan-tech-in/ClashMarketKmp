package com.clash.market.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.clash.market.theme.ClashFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClashTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFED36A), // Goldish like Clash style
                maxLines = 1,
                fontFamily = ClashFont,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {},
        actions = actions,
        modifier = modifier.background(Color(0xFF2C2C2C)), // Dark themed top bar
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF2C2C2C),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}