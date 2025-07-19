package com.clash.market.ui.screens.profile

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.components.ClashGlossyButton
import com.clash.market.components.ClashStyleButtonType
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.dialogs.ClashDialog
import com.clash.market.ui.screens.home.HomeScreenScaffold
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    onBottomBarNavigate: (ScreenRouts) -> Unit,
    onNavigate: (ScreenRouts) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ProfileContent(
        uiState = uiState,
        onBottomBarNavigate = onBottomBarNavigate,
        onNavigate = onNavigate,
        onUnlinkProfile = viewModel::unLinkProfile
    )
}

@Composable
private fun ProfileContent(
    uiState: ProfileUiState,
    onUnlinkProfile: () -> Unit,
    onBottomBarNavigate: (ScreenRouts) -> Unit,
    onNavigate: (ScreenRouts) -> Unit
) {

    var showUnLinkConfirmation by remember { mutableStateOf(false) }

    if (showUnLinkConfirmation) {
        ClashDialog(
            title = "Unlink Village?",
            description = "Chief, are you sure you want to unlink your village?",
            onDismiss = { showUnLinkConfirmation = false },
            confirmText = "Unlink",
            dismissText = "Cancel",
            onConfirm = {
                showUnLinkConfirmation = false
                onUnlinkProfile()
            },
            content = {}
        )
    }

    HomeScreenScaffold(
        currentRoute = ScreenRouts.MyProfile,
        onBottomBarNavigate = onBottomBarNavigate,
        ignoreStatusBarAlphaChange = true
    ) {
        Column(modifier = Modifier.padding(it).padding(16.dp)) {

            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterVertically)
                ) {
                    Text("Linked Village", fontSize = 12.sp)
                    Text(uiState.linkedProfile.takeIf { it.isNullOrEmpty().not() } ?: "Not linked yet")
                }

                Crossfade(uiState.linkedProfile) {
                    if (it.isNullOrEmpty()) {
                        ClashGlossyButton(
                            text = "Link",
                            onClick = { onNavigate(ScreenRouts.EnterProfile) }
                        )
                    } else {
                        ClashGlossyButton(
                            text = "UnLink",
                            type = ClashStyleButtonType.Negative,
                            onClick = { showUnLinkConfirmation = true }
                        )
                    }
                }
            }

        }
    }
}