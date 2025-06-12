package com.clash.market.ui.screens.rankings

import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.base.ResultState
import com.clash.market.components.ClashScrollableTabs
import com.clash.market.components.ClashTab
import com.clash.market.components.ResultStateLazyGridCrossFade
import com.clash.market.components.clash.ClanListItem
import com.clash.market.components.clash.PlayerInfo
import com.clash.market.models.ClanDetail
import com.clash.market.models.Player
import org.koin.mp.KoinPlatform.getKoin

internal val tabs = listOf<ClashTab>(
    ClashTab(0, "Player"),
    ClashTab(1, "Clans"),
    ClashTab(2, "Player Builder"),
    ClashTab(3, "Clan Builder")
)


@Composable
fun RankingsScreen(
    viewModel: RankingsViewModel = getKoin().get<RankingsViewModel>()
) {
    val topPlayerState by viewModel.topPlayerState.collectAsStateWithLifecycle()
    val topClanState by viewModel.topClanState.collectAsStateWithLifecycle()
    val topBuilderBasePlayerState by viewModel.topBuilderBasePlayerState.collectAsStateWithLifecycle()
    val topBuilderBaseClanState by viewModel.topBuilderBaseClanState.collectAsStateWithLifecycle()

    RankingsScreenContent(
        topPlayerState = topPlayerState,
        topClanState = topClanState,
        topBuilderBasePlayerState = topBuilderBasePlayerState,
        topBuilderBaseClanState = topBuilderBaseClanState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RankingsScreenContent(
    topPlayerState: ResultState<List<Player>>,
    topClanState: ResultState<List<ClanDetail>>,
    topBuilderBasePlayerState: ResultState<List<Player>>,
    topBuilderBaseClanState: ResultState<List<ClanDetail>>
) {

    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    ClashScrollableTabs(
        tabs = tabs,
        selectedTabIndex = selectedTabIndex,
        onTabSelected = { selectedTabIndex = it.index }
    ) {
        when (it) {
            0 -> {
                ResultStateLazyGridCrossFade(
                    resultState = topPlayerState,
                    idealContent = {}
                ) { players ->
                    items(players) { PlayerInfo(it, showClanInfo = true) }
                }
            }

            1 -> {
                ResultStateLazyGridCrossFade(
                    resultState = topClanState,
                    idealContent = {}
                ) { players ->
                    items(players) { ClanListItem(it) }
                }
            }

            2 -> {
                ResultStateLazyGridCrossFade(
                    resultState = topBuilderBasePlayerState,
                    idealContent = {}
                ) { players ->
                    items(players) { PlayerInfo(it) }
                }
            }

            3 -> {
                ResultStateLazyGridCrossFade(
                    resultState = topBuilderBaseClanState,
                    idealContent = {}
                ) { players ->
                    items(players) { ClanListItem(it) }
                }
            }
        }
    }
}