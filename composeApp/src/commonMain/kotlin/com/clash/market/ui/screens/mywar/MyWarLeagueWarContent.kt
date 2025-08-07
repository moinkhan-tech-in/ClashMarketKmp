package com.clash.market.ui.screens.mywar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_chevron_right
import coil3.compose.AsyncImage
import com.clash.market.components.AutoColumnGrid
import com.clash.market.components.ClashCard
import com.clash.market.models.ClanDetail
import com.clash.market.models.Player
import com.clash.market.models.dtos.WarLeagueGroupResponse
import com.clash.market.navigation.ScreenRouts
import org.jetbrains.compose.resources.painterResource

@Composable
fun MyWarLeagueWarContent(
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
            top = 12.dp,
            bottom = 56.dp,
            start = 12.dp, end = 12.dp
        )
    ) {

        item(span = StaggeredGridItemSpan.FullLine) {
            AutoColumnGrid(
                items = warLeagueGroupResponse.rounds.orEmpty(),
                minCellWidth = 150.dp,
                verticalSpacing = 6.dp,
                horizontalSpacing = 6.dp
            ) { item, index ->
                ClashCard(
                    onClick = { onRoundClick(
                        ScreenRouts.LeagueWarDetail(
                            title = "Round: ${index + 1}",
                            season = warLeagueGroupResponse.season.orEmpty(),
                            warTags = item.warTags
                        )
                    ) }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Round: ${index + 1}", modifier = Modifier.weight(1f))
                        Image(
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
                fontSize = 16.sp,
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = clan.badgeUrls?.medium,
                    modifier = Modifier.size(56.dp),
                    contentDescription = clan.name
                )
                Column(
                    modifier = Modifier.weight(1f).padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = clan.name.orEmpty(),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = clan.tag.orEmpty(),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Image(
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
                    .padding(top = 4.dp)
                    .padding(end = 8.dp)
                ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f),
                    text = "Members: ${members?.size ?: 0}"
                )
                Icon(
                    imageVector = if (showMembers) Icons.Default.ExpandMore else Icons.Default.ExpandLess,
                    contentDescription = null
                )
            }


            AnimatedVisibility(showMembers) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    members?.forEach { player ->
                        WarLeaguePlayerItem(
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

@Composable
private fun WarLeaguePlayerItem(player: Player, onClick: () -> Unit) {
    Row(modifier = Modifier.clickable { onClick() }.padding(start = 8.dp)) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = player.name,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = player.tag,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Image(
            painter = painterResource(Res.drawable.ic_chevron_right),
            contentDescription = null
        )
    }
}