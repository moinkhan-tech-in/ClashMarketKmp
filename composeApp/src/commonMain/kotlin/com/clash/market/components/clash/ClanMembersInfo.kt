package com.clash.market.components.clash

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_trophy
import coil3.compose.AsyncImage
import com.clash.market.components.AutoColumnGrid
import com.clash.market.components.ClashCard
import com.clash.market.components.ClashChip
import com.clash.market.models.Player

enum class SortOption {
    TROPHIES,
    NAME;

    fun label() {
        when (this) {
            NAME -> "Name"
            TROPHIES -> "Trophies"
        }
    }
}

@Composable
fun ClanMembersInfo(
    memberList: List<Player>,
    onMemberClick: (Player) -> Unit
) {
    var currentSortCriteria by remember { mutableStateOf(SortOption.entries[0]) }

    val sortedMembers = remember(currentSortCriteria) {
        sortPlayers(memberList, currentSortCriteria)
    }

    ClashCard(
        title = "Members (${sortedMembers.size}/50)",
        topEndContent = { ClashQueueChip(items = SortOption.entries) { currentSortCriteria = it } }
    ) {
        AutoColumnGrid(items = sortedMembers) { item, index ->
            ClanMemberItem(
                member = item,
                onClick = { onMemberClick(item) }
            )
        }
    }
}

@Composable
private fun ClanMemberItem(
    member: Player,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.size(56.dp),
            model = member.league?.iconUrls?.small.orEmpty(),
            contentDescription = member.name
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(member.name, style = MaterialTheme.typography.bodyLarge)
            Text(member.role?.readableName.orEmpty(), style = MaterialTheme.typography.bodySmall)
        }
        ClashChip(
            trailingImage = Res.drawable.ic_trophy,
            text = member.trophies.toString()
        )
    }
}

fun sortPlayers(players: List<Player>, sortOption: SortOption): List<Player> {
    return when (sortOption) {
        SortOption.NAME -> players.sortedBy { it.name }
        SortOption.TROPHIES -> players.sortedByDescending { it.trophies }
    }
}
