package com.clash.market.ui.screens.search.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.TagRegEx
import com.clash.market.components.ClashSearchTextFieldValue
import com.clash.market.ui.contents.playerdetail.PlayerDetailContent
import com.clash.market.ui.contents.playerdetail.PlayerDetailContentViewModel
import com.clash.market.withHashPrefixField
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SearchPlayerContent(
    viewModel: PlayerDetailContentViewModel = koinViewModel() { parametersOf("") }
) {
    val playerResultState by viewModel.playerSearchState.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.size(16.dp))
        var value by remember { mutableStateOf(TextFieldValue()) }
        ClashSearchTextFieldValue(
            modifier = Modifier.padding(horizontal = 16.dp),
            value = value,
            onValueChange = {
                if (TagRegEx.matches(it.text)) {
                    value = withHashPrefixField(it)
                }
            },
            hint = "#PlayerTag",
        )

        LaunchedEffect(value) {
            viewModel.fetchPlayer(value.text)
        }

        PlayerDetailContent(playerResultState)
    }
}
