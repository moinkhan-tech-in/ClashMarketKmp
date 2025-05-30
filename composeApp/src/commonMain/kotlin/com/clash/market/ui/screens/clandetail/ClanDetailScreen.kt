package com.clash.market.ui.screens.clandetail

import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.base.ResultState
import com.clash.market.components.ClashTopBar
import com.clash.market.models.ClanDetail
import com.clash.market.navigation.ScreenRouts
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun ClanDetailScreen(
    clanDetailRoute: ScreenRouts.ClanDetail,
    viewModel: ClanDetailViewModel = getKoin().get<ClanDetailViewModel>()
) {
    val clanDetailState = viewModel.clanDetailState.collectAsStateWithLifecycle()
    LaunchedEffect(clanDetailRoute) { viewModel.fetchClanDetail(clanDetailRoute.tag) }
    ClanDetailScreenContent(
        clanDetailRoute = clanDetailRoute,
        clanDetailState = clanDetailState.value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ClanDetailScreenContent(
    clanDetailRoute: ScreenRouts.ClanDetail,
    clanDetailState: ResultState<ClanDetail>
) {
    Scaffold(
        topBar = { ClashTopBar(title = clanDetailRoute.tag) }
    ) {

    }
}

