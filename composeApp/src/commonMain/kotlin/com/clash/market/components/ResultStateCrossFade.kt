package com.clash.market.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
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
        modifier = Modifier.fillMaxWidth(),
        targetState = resultState
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
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

@Composable
fun <T> ResultStateLazyGridCrossFade(
    resultState: ResultState<T>,
    idealContent: @Composable BoxScope.() -> Unit,
    successContent: LazyStaggeredGridScope.(T) -> Unit
) {
    Crossfade(
        modifier = Modifier.fillMaxWidth(),
        targetState = resultState
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
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
                is ResultState.Success<T> -> {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(minSize = 300.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        contentPadding = PaddingValues(
                            top = 12.dp, bottom = 56.dp,
                            start = 12.dp, end = 12.dp
                        )
                    ) {
                         successContent(it.data)
                    }
                }
            }
        }
    }
}