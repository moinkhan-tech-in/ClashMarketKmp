package com.clash.market.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_nav_logo
import com.clash.market.theme.ClashFont
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClashTopBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: DrawableResource? = Res.drawable.ic_nav_logo,
    onBackClick: (() -> Unit)? = null,
    scrollBehaviour: TopAppBarScrollBehavior? = null,
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
        navigationIcon = {
            navigationIcon?.let {
                Image(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .size(48.dp)
                        .clickable { onBackClick?.invoke() },
                    painter = painterResource(navigationIcon),
                    contentDescription = null
                )
            }
        },
        scrollBehavior = scrollBehaviour,
        actions = actions,
        modifier = modifier.background(Color(0xFF2C2C2C)), // Dark themed top bar
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF2C2C2C),
            titleContentColor = Color(0xFF2C2C2C),
            navigationIconContentColor = Color(0xFF2C2C2C),
            actionIconContentColor = Color(0xFF2C2C2C),
            scrolledContainerColor = Color(0xFF2C2C2C)
        )
    )
}