package com.clash.market.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.clash.market.theme.ClashFont
import kotlinx.coroutines.launch

@Composable
fun ClashScrollableTabs(
    tabs: List<ClashTab>,
    selectedTabIndex: Int,
    onTabSelected: (ClashTab) -> Unit,
    content: @Composable (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    Column(Modifier.fillMaxSize()) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 8.dp,
            containerColor = Color(0xFF2C2C2C), // Dark brown background
            contentColor = Color(0xFFFFD700),   // Clash gold
            indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    color = Color(0xFFFFD700),
                    height = 6.dp,
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .height(6.dp)
                )
            }
        ) {
            tabs.forEachIndexed { index, tab ->
                val isSelected = index == selectedTabIndex
                Tab(
                    selected = isSelected,
                    onClick = {
                        onTabSelected(tab)
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(tab.index)
                        }
                    },
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
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            content(page)
        }
    }
}