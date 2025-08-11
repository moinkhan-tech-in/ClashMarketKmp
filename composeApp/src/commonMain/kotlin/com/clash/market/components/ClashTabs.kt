package com.clash.market.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

data class ClashTab(
    val index: Int,
    val title: String,
    val id: String? = null
)

@Composable
fun ClashTabRows(
    pagerState: PagerState,
    tabs: List<ClashTab>,
    selectedTabIndex: Int,
    onTabSelected: (ClashTab) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color(0xFF2C2C2C), // Dark brown background
        contentColor = Color(0xFFFFD700),   // Clash gold
        indicator = { tabPositions ->
            TabRowDefaults.PrimaryIndicator(
                color = Color(0xFFFFD700),
                height = 6.dp,
                width = 120.dp,
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
                        color = if (isSelected) Color(0xFFFFD700) else Color.White
                    )
                }
            )
        }
    }
}

@Composable
fun ClashTabs(
    modifier: Modifier = Modifier,
    tabs: List<ClashTab>,
    selectedTabIndex: Int,
    onTabSelected: (ClashTab) -> Unit,
    content: @Composable (Int) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    Column(modifier.fillMaxSize()) {
        ClashTabRows(
            pagerState = pagerState,
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected
        )
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            content(page)
        }
    }
}