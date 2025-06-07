package com.clash.market.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api
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
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

data class BottomNavItem(
    val route: ScreenRouts,
    val icon: ImageVector,
    val resIcon: DrawableResource?,
    val resIconUnSelected: DrawableResource?,
    val label: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClashBottomBar(
    items: List<BottomNavItem>,
    currentRoute: ScreenRouts,
    scrollBehaviour: BottomAppBarScrollBehavior? = null,
    onItemSelected: (ScreenRouts) -> Unit
) {
    BottomAppBar(
        containerColor = Color(0xFF3B2F2F),
        contentColor = Color.White,
        scrollBehavior = scrollBehaviour
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

                if (item.resIcon != null && item.resIconUnSelected != null) {

                    Image(
                        painter = painterResource(if (selected) item.resIcon else item.resIconUnSelected),
                        contentDescription = null
                    )
                } else {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (selected) Color(0xFFFFD700) else Color.LightGray
                    )
                }
                Text(
                    text = item.label,
                    fontSize = 12.sp,
                    color = if (selected) Color(0xFFFFD700) else Color.LightGray,
                    fontFamily = ClashFont,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}