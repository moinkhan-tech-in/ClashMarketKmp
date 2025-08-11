package com.clash.market.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.clash.market.navigation.ScreenRouts
import com.clash.market.theme.LocalClashColors
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
        containerColor = MaterialTheme.colorScheme.primary,
        scrollBehavior = scrollBehaviour
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = LocalClashColors.current.clashPositive
        ) {
            items.forEach { item ->
                val selected = item.route == currentRoute
                NavigationRailItem(
                    modifier = Modifier.weight(1f),
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = LocalClashColors.current.clashPositive,
                        unselectedIconColor = Color.White.copy(alpha = .8f),
                        selectedTextColor = LocalClashColors.current.clashPositive,
                        unselectedTextColor = Color.White.copy(alpha = .8f),
                        indicatorColor = Color(0xFFFFD700)
                    ),
                    icon = {
                        if (item.resIcon != null && item.resIconUnSelected != null) {
                            Image(
                                painter = painterResource(if (selected) item.resIcon else item.resIconUnSelected),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = if (selected) MaterialTheme.colorScheme.primary else Color.White.copy(alpha = .8f)
                            )
                        }
                    },
                    onClick = { onItemSelected(item.route) },
                    selected = selected,
                    label = {
                        Text(
                            modifier = Modifier.padding(vertical = 2.dp),
                            text = item.label,
                            color = if (selected) Color(0xFFFFD700) else Color.White.copy(alpha = .8f),
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }
    }
}