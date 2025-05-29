package com.clash.market.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clash.market.base.ResultState

@Composable
fun <T> ResultStateCrossFade(
    resultState: ResultState<T>,
    idealContent: @Composable BoxScope.() -> Unit,
    successContent: @Composable BoxScope.(T) -> Unit
) {
    Crossfade(
        modifier = Modifier.fillMaxSize(),
        targetState = resultState
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            when (it) {
                is ResultState.Error -> {
                    Text(
                        modifier = Modifier.padding(top = 120.dp),
                        text = "Oops, Something went wrong. [${it.message}]"
                    )
                }
                ResultState.Ideal -> idealContent()
                ResultState.Loading -> {
                    ClashLoadingIndicator(modifier = Modifier.padding(top = 120.dp))
                }
                is ResultState.Success<T> -> successContent(it.data)
            }
        }
    }
}