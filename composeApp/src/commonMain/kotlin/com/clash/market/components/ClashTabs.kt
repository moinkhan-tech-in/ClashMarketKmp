package com.clash.market.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.clash.market.theme.ClashFont

data class ClashTab(
    val title: String
)

@Composable
fun ClashTabs(
    tabs: List<ClashTab>,
    selectedTab: ClashTab,
    onTabSelected: (ClashTab) -> Unit,
    content: @Composable (Int) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = tabs.indexOf(selectedTab),
            containerColor = Color(0xFF2C2C2C), // Dark brown background
            contentColor = Color(0xFFFFD700),   // Clash gold
            indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    color = Color(0xFFFFD700),
                    height = 6.dp,
                    width = 120.dp,
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[tabs.indexOf(selectedTab)])
                        .height(6.dp)
                )
            }
        ) {
            tabs.forEachIndexed { index, tab ->
                val isSelected = tab == selectedTab
                Tab(
                    selected = isSelected,
                    onClick = { onTabSelected(tab) },
                    text = {
                        Text(
                            tab.title,
                            fontFamily = ClashFont,
                            color = if (isSelected) Color(0xFFFFD700) else Color.White
                        )
                    }
                )
            }
        }
        Crossfade(
            modifier = Modifier.fillMaxSize(),
            targetState = selectedTab
        ) {
            content(tabs.indexOf(selectedTab))
        }
    }
}