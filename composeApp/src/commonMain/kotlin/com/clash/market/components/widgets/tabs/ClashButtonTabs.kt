package com.clash.market.components.widgets.tabs

import PinnedCenterRowDynamic
import SideArrangement
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import centerPinned
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_sward
import com.clash.market.ClashTheme
import com.clash.market.components.widgets.ClashImage
import com.clash.market.components.widgets.ClashImageSpec
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

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
        PinnedCenterRowDynamic(sideArrangement = SideArrangement.Start) {
            tabs.forEachIndexed { index, item ->
                ToggleButton(
                    modifier = when (tabs.size.floorDiv(2)) {
                        index -> Modifier.centerPinned()
                        else -> Modifier.fillMaxWidth().padding(2.dp)
                    },
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
                        width = if (index == selectedTabIndex) 1.dp else 0.dp,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    colors = ToggleButtonDefaults.toggleButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        checkedContainerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary,
                        checkedContentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 4.dp,
                            alignment = when (index) {
                                0 -> Alignment.Start
                                tabs.lastIndex -> Alignment.End
                                else -> Alignment.CenterHorizontally
                            }
                        )
                    ) {
                        ClashButtonTabItem(item)
                    }
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

@Composable
fun RowScope.ClashButtonTabItem(item: ClashTab) {
    item.leadingImage?.let {
        ClashImage(
            modifier = Modifier.size(28.dp),
            image = it
        )
    }
    item.title?.let {
        Text(
            modifier = Modifier.basicMarquee(iterations = 10),
            maxLines = 1,
            text = it,
            style = MaterialTheme.typography.labelSmall
        )
    }
    item.trailingImage?.let {
        ClashImage(
            modifier = Modifier.size(24.dp),
            image = it)
    }
}

@Composable
@Preview
fun ClashButtonTabsPreview() {
    ClashTheme {
        ClashButtonTabs(
            modifier = Modifier.fillMaxWidth(),
            tabs = listOf(
                ClashTab(0, leadingImage = ClashImageSpec.Res(Res.drawable.ic_sward)),
                ClashTab(1, leadingImage = ClashImageSpec.Res(Res.drawable.ic_sward)),
                ClashTab(2, leadingImage = ClashImageSpec.Res(Res.drawable.ic_sward)),
            ),
            selectedTabIndex = 1,
            onTabSelected = {},
        ) {

        }
    }
}