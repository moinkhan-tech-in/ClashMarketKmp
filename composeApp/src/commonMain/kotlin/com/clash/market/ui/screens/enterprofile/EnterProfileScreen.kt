package com.clash.market.ui.screens.enterprofile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_nav_logo
import com.clash.market.base.ResultState
import com.clash.market.components.ClashGlossyButton
import com.clash.market.components.ClashStyleButtonType
import com.clash.market.components.ClashTextField
import com.clash.market.components.clash.ClashScaffold
import com.clash.market.components.clash.PlayerInfo
import com.clash.market.models.Player
import com.clash.market.navigation.ScreenRouts
import com.clash.market.theme.ClashFont
import com.clash.market.theme.LocalClashColors
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EnterProfileScreen(
    viewModel: EnterProfileViewModel = koinViewModel(),
    onNavigate: (ScreenRouts) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    EnterProfileContent(
        uiState = uiState,
        onNavigate = onNavigate,
        onVerification = viewModel::onProfileVerification,
        onSaveTagIfAvailable = viewModel::onSaveTagIfAvailable,
        onPlayerTagSubmitted = viewModel::onPlayerTagSubmitted,
        onPlayerTagChanged = viewModel::onPlayerTagChanged,
        onSkipClick = viewModel::onSkipClick
    )
}

@Composable
private fun EnterProfileContent(
    uiState: EnterProfileUiState,
    onNavigate: (ScreenRouts) -> Unit = {},
    onSaveTagIfAvailable: () -> Unit = {},
    onVerification: () -> Unit = {},
    onPlayerTagSubmitted: (String) -> Unit = {},
    onPlayerTagChanged: (String) -> Unit = {},
    onSkipClick: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    ClashScaffold(
        title = "Claim Your Village!",
        navigationIcon = Res.drawable.ic_nav_logo
    ) {
        Column(
            Modifier
                .padding(it)
                .padding(16.dp)
                .imePadding()
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Spacer(Modifier.size(10.dp))

            Text(
                text = "Drop Your Tag, Chief!",
                fontSize = 18.sp,
                fontFamily = ClashFont,
                color = Color(0xFF3E2723) // Dark brown
            )

            var playerTag by rememberSaveable { mutableStateOf("") }
            ClashTextField(
                value = playerTag,
                onValueChange = { playerTag = it.uppercase() },
                hint = "#YourTagHere"
            )

            Crossfade(uiState.playerResultState) { state ->
                when (state) {
                    is ResultState.Error -> {
                        Text(
                            text = "Uh-oh, Chief! That tag doesn't belong to any known village",
                            fontSize = 16.sp,
                            fontFamily = ClashFont,
                            lineHeight = 18.sp,
                            color = LocalClashColors.current.clashNegative
                        )
                    }

                    ResultState.Ideal -> {}
                    ResultState.Loading -> {
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth(),
                            strokeCap = StrokeCap.Round,
                            color = MaterialTheme.colorScheme.onPrimary,
                            trackColor = MaterialTheme.colorScheme.primary
                        )
                    }

                    is ResultState.Success<Player> -> {
                        Text(
                            text = "You're in, Chief ${state.data.name}!",
                            fontSize = 16.sp,
                            fontFamily = ClashFont,
                            color = LocalClashColors.current.clashPositive
                        )
                    }
                }
            }

            Spacer(Modifier.size(12.dp))

            AnimatedVisibility(uiState.playerResultState is ResultState.Success) {
                val player = (uiState.playerResultState as? ResultState.Success<Player>)?.data
                player?.let { it1 -> PlayerInfo(it1) }
            }

//            AnimatedVisibility(uiState.playerResultState is ResultState.Success) {
//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    Text(
//                        text = "Token (Optional)",
//                        fontSize = 16.sp,
//                        fontFamily = ClashFont,
//                        color = Color(0xFF3E2723) // Dark brown
//                    )
//
//                    var token by remember { mutableStateOf("") }
//                    ClashTextField(
//                        value = token,
//                        onValueChange = { token = it },
//                        hint = "Token"
//                    )
//                }
//            }

            Spacer(Modifier.weight(1f))

            AnimatedVisibility((uiState.playerResultState is ResultState.Success).not()) {
                Row(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                ) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF1B5F75))
                    Spacer(Modifier.size(8.dp))
                    Text(
                        text = "Drop your tag once, Chief! We’ll keep your stats ready every time you return.",
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(Modifier.size(8.dp))

            AnimatedVisibility((uiState.playerResultState is ResultState.Success).not()) {
                ClashGlossyButton(
                    modifier = Modifier.fillMaxWidth(),
                    type = ClashStyleButtonType.Negative,
                    text = "Skip",
                    onClick = {
                        onSkipClick()
                        onNavigate(ScreenRouts.Home)
                    }
                )
            }

            val isEnabled by remember { derivedStateOf { playerTag.length == 10 } }

            if (isEnabled) {
                onPlayerTagChanged(playerTag)
            }

            Crossfade(targetState = uiState.playerResultState) {
                if (it is ResultState.Success) {
                    ClashGlossyButton(
                        modifier = Modifier.fillMaxWidth(),
                        type = ClashStyleButtonType.Positive,
                        text = "Continue as ${it.data.name}",
                        enabled = isEnabled,
                        onClick = {
                            onSaveTagIfAvailable()
                            onNavigate.invoke(ScreenRouts.Home)
                        }
                    )
                } else {
                    ClashGlossyButton(
                        modifier = Modifier.fillMaxWidth(),
                        type = ClashStyleButtonType.Positive,
                        text = "Submit",
                        enabled = isEnabled,
                        onClick = {
                            keyboardController?.hide()
                            onPlayerTagSubmitted(playerTag)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun EnterProfileContentPreview() {
    EnterProfileContent(uiState = EnterProfileUiState())
}