package com.clash.market.ui.screens.mywar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_chevron_right
import clashmarket.composeapp.generated.resources.ic_chevron_up
import coil3.compose.AsyncImage
import com.clash.market.components.AutoColumnGrid
import com.clash.market.components.ClashCard
import com.clash.market.components.PlayerListItem
import com.clash.market.models.ClanDetail
import com.clash.market.models.dtos.WarLeagueGroupResponse
import com.clash.market.navigation.ScreenRouts
import org.jetbrains.compose.resources.painterResource

@Composable
fun MyWarLeagueWarContent(
    innerPaddingValues: PaddingValues,
    warLeagueGroupResponse: WarLeagueGroupResponse,
    onRoundClick: (ScreenRouts.LeagueWarDetail) -> Unit,
    onClanClick: (String) -> Unit = {},
    onPlayerClick: (String) -> Unit = {},
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 300.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalItemSpacing = 4.dp,
        contentPadding = PaddingValues(
            top = innerPaddingValues.calculateTopPadding() + 12.dp,
            bottom = innerPaddingValues.calculateBottomPadding() + 56.dp,
            start = innerPaddingValues.calculateStartPadding(LocalLayoutDirection.current) + 12.dp,
            end = innerPaddingValues.calculateEndPadding(LocalLayoutDirection.current) + 12.dp
        )
    ) {

        item(span = StaggeredGridItemSpan.FullLine) {
            AutoColumnGrid(
                items = warLeagueGroupResponse.rounds.orEmpty(),
                minCellWidth = 150.dp,
                verticalSpacing = 4.dp,
                horizontalSpacing = 4.dp
            ) { item, index ->
                ClashCard(
                    onClick = {
                        onRoundClick(
                            ScreenRouts.LeagueWarDetail(
                                title = "Round: ${index + 1}",
                                season = warLeagueGroupResponse.season.orEmpty(),
                                warTags = item.warTags
                            )
                        )
                    }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Round: ${index + 1}",
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.labelMedium
                        )
                        Image(
                            modifier = Modifier.size(28.dp),
                            painter = painterResource(Res.drawable.ic_chevron_right),
                            contentDescription = null
                        )
                    }
                }
            }
        }

        item(span = StaggeredGridItemSpan.FullLine) {
            Text(
                modifier = Modifier.padding(top = 32.dp, bottom = 8.dp, start = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                text = "Participating Clans"
            )
        }

        items(warLeagueGroupResponse.clans.orEmpty()) { clan ->
            WarLeagueClanItem(
                clan = clan,
                onClanClick = onClanClick,
                onPlayerClick = onPlayerClick
            )
        }
    }
}


@Composable
private fun WarLeagueClanItem(
    clan: ClanDetail,
    onClanClick: (String) -> Unit = {},
    onPlayerClick: (String) -> Unit = {},
) {
    ClashCard {
        Column(
            modifier = Modifier.padding(start = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.clickable { onClanClick(clan.tag.orEmpty()) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = clan.badgeUrls?.medium,
                    modifier = Modifier.size(56.dp),
                    contentDescription = clan.name
                )
                Column(modifier = Modifier.weight(1f).padding(12.dp)) {
                    Text(
                        text = clan.name.orEmpty(),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(Modifier.size(4.dp))
                    Text(
                        text = clan.tag.orEmpty(),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Image(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(Res.drawable.ic_chevron_right),
                    contentDescription = null
                )
            }

            HorizontalDivider(Modifier.padding(start = 8.dp))

            val members = clan.members?.safeMembers()
            var showMembers by rememberSaveable { mutableStateOf(false) }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showMembers = showMembers.not() }
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.weight(1f),
                    text = "Members: ${members?.size ?: 0}"
                )

                val rotateState by animateIntAsState(
                    when (showMembers) {
                        true -> 0
                        else -> 180
                    }
                )

                Image(
                    modifier = Modifier.size(28.dp).graphicsLayer{ rotationZ = rotateState.toFloat() },
                    painter = painterResource(  Res.drawable.ic_chevron_up),
                    contentDescription = null
                )
            }


            AnimatedVisibility(showMembers) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    members?.forEach { player ->
                        PlayerListItem(
                            player = player,
                            onClick = { onPlayerClick(player.tag) }
                        )
                        HorizontalDivider(Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
    }
}