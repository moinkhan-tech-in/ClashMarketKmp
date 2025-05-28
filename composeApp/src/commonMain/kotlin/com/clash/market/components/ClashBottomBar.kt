package com.clash.market.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clash.market.navigation.ScreenRouts
import com.clash.market.theme.ClashFont

data class BottomNavItem(
    val route: ScreenRouts,
    val icon: ImageVector,
    val label: String
)

@Composable
fun ClashBottomBar(
    items: List<BottomNavItem>,
    currentRoute: ScreenRouts,
    onItemSelected: (ScreenRouts) -> Unit
) {
    BottomAppBar(
        containerColor = Color(0xFF3B2F2F),
        contentColor = Color.White
    ) {
        print(currentRoute)
        items.forEach { item ->
            val selected = item.route == currentRoute
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onItemSelected(item.route) }
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = if (selected) Color(0xFFFFD700) else Color.LightGray
                )
                Text(
                    text = item.label,
                    fontSize = 10.sp,
                    color = if (selected) Color(0xFFFFD700) else Color.LightGray,
                    fontFamily = ClashFont,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}