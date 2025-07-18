package com.clash.market.ui.contents.clandetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.components.ResultStateCrossFade
import com.clash.market.components.clash.ClanCapitalInfo
import com.clash.market.components.clash.ClanListItem
import com.clash.market.components.clash.ClanMembersInfo
import com.clash.market.navigation.ScreenRouts
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ClanDetailContent(
    clanTag: String,
    onNavigate: (ScreenRouts) -> Unit,
    viewModel: ClanDetailContentViewModel = koinViewModel(),
    topPadding: Dp = 12.dp
) {
    val clanDetailState = viewModel.clanDetailState.collectAsStateWithLifecycle()
    LaunchedEffect(clanTag) { viewModel.fetchClanDetail(clanTag) }
    ResultStateCrossFade(
        resultState = clanDetailState.value,
        topPadding = topPadding + 100.dp,
        idealContent = {}
    ) { clanDetail ->

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(minSize = 300.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalItemSpacing = 4.dp,
            contentPadding = PaddingValues(
                top = topPadding, bottom = 56.dp,
                start = 12.dp, end = 12.dp
            )
        ) {
            item {
                ClanListItem(
                    clanDetail = clanDetail,
                    onWarLogsClick = {
                        onNavigate.invoke(
                            ScreenRouts.WarLogs(clanDetail.tag.orEmpty(), clanDetail.name.orEmpty())
                        )
                    })
            }

            item {
                clanDetail.clanCapital?.let {
                    ClanCapitalInfo(
                        points = clanDetail.clanCapitalPoints ?: 0,
                        detail = clanDetail.clanCapital,
                        league = clanDetail.capitalLeague
                    )
                }
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                ClanMembersInfo(
                    memberList = clanDetail.memberList.orEmpty(),
                    onMemberClick = {
                        onNavigate(ScreenRouts.PlayerDetail(tag = it.tag, name = it.name))
                    }
                )
            }
        }
    }
}