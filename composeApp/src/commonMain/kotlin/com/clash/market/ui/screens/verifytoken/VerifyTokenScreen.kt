package com.clash.market.ui.screens.verifytoken

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_back
import com.clash.market.components.ClashPositiveButton
import com.clash.market.components.ClashTextField
import com.clash.market.components.ClashTopBar
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyAccountScreen(
    viewModel: VerifyAccountViewModel = koinViewModel(),
    onBack: () -> Unit
) {

    Scaffold(
        topBar = {
            ClashTopBar(
                title = "Verify Account",
                navigationIcon = Res.drawable.ic_back,
                onBackClick = onBack
            )
        }
    ) {
//        VerifyTokenScreenContent(
//            onSubmit = viewModel::onTokenSubmit
//        )
    }
}

@Composable
private fun VerifyTokenScreenContent(
    onSubmit: (String) -> Unit
) {
    var token by remember { mutableStateOf("") }
    var showHelpDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            "Paste your API Token from the Clash of Clans app to verify your account.",
            style = MaterialTheme.typography.bodyMedium
        )

        ClashTextField(
            value = token,
            onValueChange = { token = it },
            hint = "API Token",
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = { showHelpDialog = true }) {
                Text("Where do I find this?")
            }
        }

        ClashPositiveButton(
            text = "Verify",
            onClick = { onSubmit(token.trim()) },
            enabled = token.isNotBlank()
        )
    }

    if (showHelpDialog) {
        AlertDialog(
            onDismissRequest = { showHelpDialog = false },
            title = { Text("How to get your API Token") },
            text = {
                Text(
                    """
                    1. Open Clash of Clans
                    2. Go to Settings → More Settings
                    3. Tap API Token
                    4. Copy and paste it here
                    
                    This token is safe, read-only, and can be changed anytime in-game.
                    """.trimIndent()
                )
            },
            confirmButton = {
                TextButton(onClick = { showHelpDialog = false }) {
                    Text("Got it")
                }
            }
        )
    }
}