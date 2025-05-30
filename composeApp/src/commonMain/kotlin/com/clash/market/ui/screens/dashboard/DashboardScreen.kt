package com.clash.market.ui.screens.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_coc_xp
import coil3.compose.AsyncImage
import com.clash.market.components.ClashCard
import com.clash.market.components.ClashPositiveButton
import com.clash.market.components.ClashTooltipBox
import com.clash.market.models.Label
import com.clash.market.theme.ClashFont
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = getKoin().get<DashboardViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DashboardScreenContent(uiState = uiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenContent(uiState: DashboardUiState) {
    AnimatedVisibility(uiState.player != null) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            PlayerInfo(
                name = uiState.player?.name.orEmpty(),
                tag = uiState.player?.tag.orEmpty(),
                exp = uiState.player?.expLevel ?: 0,
                clanRole = uiState.player?.role.orEmpty(),
                onEdit = {}
            )
            ClanInfo(
                name = uiState.player?.clan?.name.orEmpty(),
                tag = uiState.player?.clan?.tag.orEmpty(),
                clanImage = uiState.player?.clan?.badgeUrls?.small.orEmpty(),
                onShare = {}
            )
        }
    }
}

@Composable
private fun ClanInfo(
    name: String,
    tag: String,
    clanImage: String,
    onShare: () -> Unit
) {
    ClashCard(title = "Clan") {
        Row {
            AsyncImage(
                model = clanImage,
                modifier = Modifier.size(56.dp),
                contentDescription = name
            )
            Column(
                modifier = Modifier.weight(1f).padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = name, fontFamily = ClashFont)
                Text(text = tag, fontFamily = ClashFont)
            }
            ClashPositiveButton(
                text = "Share",
                onClick = onShare,
                rightIcon = Icons.Default.Share
            )
        }
    }
}

@Composable
@Preview
private fun PlayerInfo(
    name: String,
    tag: String,
    exp: Int,
    clanRole: String,
    onEdit: () -> Unit
) {
    ClashCard(title = "Player") {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier.size(56.dp),
                    painter = painterResource(Res.drawable.ic_coc_xp), contentDescription = null
                )
                Text(
                    text = exp.toString(),
                    fontFamily = ClashFont,
                    color = Color.White,
                    fontSize = 20.sp,
                    style = TextStyle(shadow = Shadow(color = Color.Black, blurRadius = 10f))
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = name, fontFamily = ClashFont)
                Text(text = tag, fontFamily = ClashFont)
                Text(text = clanRole, fontFamily = ClashFont)
            }
            ClashPositiveButton(
                text = "Edit",
                onClick = onEdit,
                rightIcon = Icons.Default.Edit
            )
        }
    }
}

@Composable
@Preview
private fun PlayerLabel(
    labels: List<Label>
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        labels.forEach {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ClashTooltipBox(tooltipText = it.name) {
                    AsyncImage(
                        model = it.iconUrls.medium,
                        contentDescription = null
                    )
                }
            }
        }
    }
}