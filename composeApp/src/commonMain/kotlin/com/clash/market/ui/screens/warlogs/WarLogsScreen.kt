package com.clash.market.ui.screens.warlogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_back
import com.clash.market.base.ResultState
import com.clash.market.components.ClashTopBar
import com.clash.market.components.ResultStateCrossFade
import com.clash.market.components.clash.ClanCurrentWarInfo
import com.clash.market.models.dtos.CurrentWarResponse
import com.clash.market.navigation.ScreenRouts
import org.koin.compose.viewmodel.koinViewModel

internal val WinGradiant = Brush.horizontalGradient(
    listOf(
        Color(0xFF388E3C).copy(alpha = .6f),
        Color.Transparent,
        Color.Transparent,
        Color(0xFFB71C1C).copy(alpha = .6f)
    )
)
internal val LossGradiant = Brush.horizontalGradient(
    listOf(
        Color(0xFFB71C1C).copy(alpha = .6f),
        Color.Transparent,
        Color.Transparent,
        Color(0xFF388E3C).copy(alpha = .6f)
    )
)

@Composable
fun WarLogsScreen(
    warLogsRoute: ScreenRouts.WarLogs,
    viewModel: WarLogsViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    LaunchedEffect(Unit) { viewModel.fetchWarLogs(warLogsRoute.tag) }
    val uiState = viewModel.warLogsState.collectAsStateWithLifecycle()
    WarLogsScreenContent(
        clanName = warLogsRoute.name,
        uiState = uiState.value,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WarLogsScreenContent(
    clanName: String,
    uiState: ResultState<List<CurrentWarResponse>>,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            ClashTopBar(
                title = clanName,
                navigationIcon = Res.drawable.ic_back,
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            ResultStateCrossFade(
                resultState = uiState,
                idealContent = { }
            ) { result ->
                LazyVerticalGrid(
                    columns = Adaptive(minSize = 300.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(
                        top = padding.calculateTopPadding() + 16.dp,
                        bottom = 56.dp
                    )
                ) {
                    items(result) { it ->
                        ClanCurrentWarInfo(war = it)
                    }
                }
            }
        }
    }
}