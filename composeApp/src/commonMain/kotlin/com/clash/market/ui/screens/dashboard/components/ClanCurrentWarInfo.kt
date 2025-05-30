package com.clash.market.ui.screens.dashboard.components

import androidx.compose.runtime.Composable
import com.clash.market.components.ClashCard
import com.clash.market.models.dtos.CurrentWarResponse

@Composable
internal fun ClanCurrentWarInfo(
    war: CurrentWarResponse
) {
    ClashCard(title = "Current War - ${war.state.readableName}") {

    }
}