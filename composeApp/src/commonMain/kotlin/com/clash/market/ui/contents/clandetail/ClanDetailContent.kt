package com.clash.market.ui.contents.clandetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.components.ResultStateCrossFade
import com.clash.market.components.clash.ClanCapitalInfo
import com.clash.market.components.clash.ClanListItem
import com.clash.market.components.clash.ClanMembersInfo
import com.clash.market.navigation.ScreenRouts
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun ClanDetailContent(
    clanTag: String,
    onNavigate: (ScreenRouts) -> Unit,
    viewModel: ClanDetailContentViewModel = getKoin().get<ClanDetailContentViewModel>()
) {
    val clanDetailState = viewModel.clanDetailState.collectAsStateWithLifecycle()
    LaunchedEffect(clanTag) { viewModel.fetchClanDetail(clanTag) }
    ResultStateCrossFade(
        resultState = clanDetailState.value,
        idealContent = {}
    ) { clanDetail ->

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(minSize = 300.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(
                top = 12.dp, bottom = 56.dp,
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

            item {
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