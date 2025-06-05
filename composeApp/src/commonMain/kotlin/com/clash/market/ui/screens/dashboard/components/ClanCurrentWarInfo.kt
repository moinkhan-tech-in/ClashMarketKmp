package com.clash.market.ui.screens.dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_star
import clashmarket.composeapp.generated.resources.ic_sward
import coil3.compose.AsyncImage
import com.clash.market.components.AnimatedTimeText
import com.clash.market.components.ClashCard
import com.clash.market.models.ClanDetail
import com.clash.market.models.WarState
import com.clash.market.models.dtos.CurrentWarResponse
import com.clash.market.models.dtos.FakeCurrentWarResponse
import com.clash.market.theme.ClashFont
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ClanCurrentWarInfo(
    war: CurrentWarResponse
) {
    ClashCard(
        title = "State - ${war.state.readableName}",
        topEndContent = {
            ClanWarTopEndContent(war)
        }
    ) {
        when (war.state) {
            WarState.IN_WAR -> {
                ClanInWarCard(war)
            }

            WarState.NOT_IN_WAR -> {

            }

            WarState.PREPARATION -> {

            }

            WarState.WAR_ENDED -> {

            }
        }
    }
}

@Composable
fun ClanWarTopEndContent(war: CurrentWarResponse) {
    when (war.state) {
        WarState.NOT_IN_WAR -> {}
        WarState.PREPARATION, WarState.IN_WAR -> {
            var remainTime by remember { mutableStateOf("") }
            LaunchedEffect(Unit) {
                while (true) {
                    remainTime = when (war.state) {
                        WarState.PREPARATION -> war.getPreparationTime()
                        WarState.IN_WAR -> war.getWarEndTime()
                        else -> ""
                    }
                    delay(1000)
                }
            }

            AnimatedTimeText(remainTime)
        }

        WarState.WAR_ENDED -> {}
    }
}

@Composable
private fun ClanInWarCard(
    warResponse: CurrentWarResponse
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ClanCard(clan = warResponse.clan)
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "VS",
                fontFamily = ClashFont,
                color = Color(0xFF8B6508),
                fontSize = 22.sp,
                style = TextStyle.Default.copy(
                    shadow = Shadow(color = Color.Black, blurRadius = 2f)
                )
            )
            Spacer(Modifier.size(18.dp))
            Text(text = warResponse.teamSize.toString(), fontFamily = ClashFont)
            Icon(Icons.Default.Groups, contentDescription = null)
        }
        ClanCard(clan = warResponse.opponent)
    }
}

@Composable
private fun ClanCard(
    clan: ClanDetail
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = clan.badgeUrls?.medium,
            modifier = Modifier.size(56.dp),
            contentDescription = clan.name
        )
        Text(text = clan.name.orEmpty(), fontFamily = ClashFont)
        Text(text = clan.tag.orEmpty(), fontFamily = ClashFont)
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = clan.stars.toString(), fontFamily = ClashFont)
            Spacer(Modifier.size(4.dp))
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(Res.drawable.ic_star),
                contentDescription = null
            )

            Spacer(Modifier.size(12.dp))

            Text(text = clan.attacks.toString(), fontFamily = ClashFont)
            Spacer(Modifier.size(4.dp))
            Image(
                modifier = Modifier.size(28.dp),
                painter = painterResource(Res.drawable.ic_sward),
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
private fun ClanInWarCardPreview() {
    ClanInWarCard(FakeCurrentWarResponse)
}