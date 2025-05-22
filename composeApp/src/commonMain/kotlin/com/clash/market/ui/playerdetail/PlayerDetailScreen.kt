package com.clash.market.ui.playerdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.PlayerDetailRoute
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun PlayerDetailScreen(
    playerDetailRoute: PlayerDetailRoute,
    viewModel: PlayerDetailViewModel = getKoin().get<PlayerDetailViewModel>()
) {
    viewModel.fetchPlayerDetail(playerDetailRoute.tag)
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    PlayerDetailScreenContent(uiState.value)
}

@Composable
private fun PlayerDetailScreenContent(value: PlayerDetailUiState) {
    AnimatedVisibility(visible = value.player != null) {
        Column {
            Text(text = value.player?.name.orEmpty())
            Text(text = value.player?.tag.orEmpty())
            Text(text = value.player?.expLevel.toString())
        }
    }
}