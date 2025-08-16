package com.clash.market.components.widgets.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.clash.market.components.widgets.ClashImageSpec
import kotlinx.coroutines.launch

data class ClashTab(
    val index: Int,
    val leadingImage: ClashImageSpec? = null,
    val title: String? = null,
    val trailingImage: ClashImageSpec? = null,
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
    PrimaryTabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color(0xFF2C2C2C), // Dark brown background
        contentColor = Color(0xFFFFD700),   // Clash gold
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                color = Color(0xFFFFD700),
                height = 6.dp,
                width = 120.dp,
                modifier = Modifier.tabIndicatorOffset(selectedTabIndex)
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
                text = { ClashTabItem(tab, isSelected) }
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