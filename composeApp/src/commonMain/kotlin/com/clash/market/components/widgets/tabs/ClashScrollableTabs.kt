package com.clash.market.components.widgets.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ClashScrollableTabs(
    modifier: Modifier,
    tabs: List<ClashTab>,
    selectedTabIndex: Int,
    onTabSelected: (ClashTab) -> Unit,
    content: @Composable (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    Column(modifier.fillMaxSize()) {
        PrimaryScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 8.dp,
            containerColor = MaterialTheme.colorScheme.primary, // Dark brown background
            contentColor = MaterialTheme.colorScheme.onPrimary,   // Clash gold
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    Modifier
                        .tabIndicatorOffset(selectedTabIndex, matchContentSize = false)
                        .clip(RoundedCornerShape(8.dp)),
                    height = 6.dp,
                    color = Color(0xFFFFD700)
                )
            }
        ) {
            tabs.forEachIndexed { index, tab ->
                val isSelected = index == selectedTabIndex
                Tab(
                    selected = isSelected,
                    onClick = {
                        onTabSelected(tab)
                        coroutineScope.launch { pagerState.animateScrollToPage(tab.index) }
                    },
                    text = { ClashTabItem(tab, isSelected) }
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