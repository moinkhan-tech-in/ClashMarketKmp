package com.clash.market.ui.playerdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.navigation.ScreenRouts
import com.clash.market.theme.ClashFont
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun PlayerDetailScreen(
    playerDetailRoute: ScreenRouts.PlayerDetail,
    viewModel: PlayerDetailViewModel = getKoin().get<PlayerDetailViewModel>()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    PlayerDetailScreenContent(
        value = uiState.value
    )
}

@Composable
private fun PlayerDetailScreenContent(
    value: PlayerDetailUiState
) {

    AnimatedVisibility(visible = value.player != null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value.player?.name.orEmpty(), fontFamily = ClashFont)
            Text(text = value.player?.tag.orEmpty(), fontFamily = ClashFont)
            Text(text = value.player?.expLevel.toString(), fontFamily = ClashFont)
        }
    }
}

