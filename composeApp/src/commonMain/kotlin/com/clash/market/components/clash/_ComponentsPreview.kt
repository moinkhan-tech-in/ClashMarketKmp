package com.clash.market.components.clash

import androidx.compose.runtime.Composable
import com.clash.market.models.FakeClanDetailItem
import com.clash.market.models.FakePlayer
import com.clash.market.models.WarState
import com.clash.market.models.dtos.CurrentWarResponse
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
private fun PlayerInfoPreview() {
    PlayerInfo(
        player = FakePlayer
    ) { }
}

@Composable
@Preview
private fun ClanInfoPreview() {
    ClanInfo(
        name = "Avengers",
        tag = "#123442",
        clanImage = "",
        onShare = {}
    )
}

@Composable
@Preview
private fun ClanCurrentWarInfoPreview() {
    ClanCurrentWarInfo(
        war = CurrentWarResponse(
            state = WarState.PREPARATION,
            teamSize = 15,
            clan = FakeClanDetailItem,
            opponent = FakeClanDetailItem
        )
    )
}