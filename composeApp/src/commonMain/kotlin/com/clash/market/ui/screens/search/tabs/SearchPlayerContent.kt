package com.clash.market.ui.screens.search.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clash.market.base.ResultState
import com.clash.market.components.ClashSearchTextField
import com.clash.market.components.ResultStateCrossFade
import com.clash.market.components.clash.ClanInfo
import com.clash.market.components.clash.PlayerAchievementInfo
import com.clash.market.components.clash.PlayerInfo
import com.clash.market.components.clash.PlayerItemsInfoFlowRow
import com.clash.market.models.Player
import com.clash.market.theme.ClashFont

@Composable
fun SearchPlayerContent(
    playerSearchState: ResultState<Player>,
    onQuerySubmit: (String) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Spacer(Modifier.size(16.dp))

        var value by remember { mutableStateOf("#2GYCPJJY2") }
        ClashSearchTextField(
            value = value,
            onQuerySubmit = onQuerySubmit,
            onValueChange = { value = it.uppercase() },
            btnText = "Search",
            hint = "#PlayerTag",
            btnEnabled = { value.length >= 10 }
        )

        ResultStateCrossFade(
            resultState = playerSearchState,
            idealContent = {
                Text(
                    modifier = Modifier.padding(top = 120.dp),
                    text = "Chief, Start searching for a player.",
                    fontFamily = ClashFont
                )
            }
        ) { result ->
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(minSize = 300.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(
                    top = 12.dp,
                    bottom = 56.dp
                )
            ) {
                item {
                    PlayerInfo(player = result)
                }

                item {
                    if (result.clan != null) {
                        ClanInfo(
                            name = result.clan.name.orEmpty(),
                            tag = result.clan.tag.orEmpty(),
                            clanImage = result.clan.badgeUrls?.medium.orEmpty(),
                            onShare = {}
                        )
                    }
                }

                item {
                    PlayerItemsInfoFlowRow(
                        title = "Troops",
                        playerItems = result.troops
                    )
                }

                item {
                    PlayerItemsInfoFlowRow(
                        title = "Spells",
                        playerItems = result.spells
                    )
                }

                item {
                    PlayerItemsInfoFlowRow(
                        title = "Hero",
                        playerItems = result.heroes
                    )
                }

                item {
                    PlayerItemsInfoFlowRow(
                        title = "Hero Equipment",
                        playerItems = result.heroEquipment
                    )
                }

                item {
                    PlayerAchievementInfo(result.achievements)
                }
            }
        }
    }
}
