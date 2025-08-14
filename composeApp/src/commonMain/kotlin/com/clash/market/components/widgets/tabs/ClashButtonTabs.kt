package com.clash.market.components.widgets.tabs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ClashButtonTabs(
    modifier: Modifier = Modifier,
    tabs: List<ClashTab>,
    selectedTabIndex: Int,
    onTabSelected: (ClashTab) -> Unit,
    content: @Composable (Int) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    Column(modifier) {
        Row(horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween)) {
            tabs.forEachIndexed { index, item ->
                ToggleButton(
                    modifier = Modifier.weight(1f),
                    checked = selectedTabIndex == index,
                    onCheckedChange = {
                        onTabSelected(item)
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(item.index)
                        }
                    },
                    shapes = when (index) {
                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                        tabs.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                    },
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.primary.copy(alpha = .3f)
                    ),
                    colors = ToggleButtonDefaults.toggleButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    )
                ) {
                    Text(
                        maxLines = 1,
                        text = item.title,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            beyondViewportPageCount = 1
        ) { page ->
            content(page)
        }
    }
}