package com.clash.market.helper// commonMain (KMP-safe)

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> StaggeredPopInGrid(
    items: List<T>,
    key: (T) -> Any = { it as Any },
    baseDelayMs: Int = 40,          // start delay
    stepDelayMs: Int = 30,          // per-index additional delay
    enterMs: Int = 260, exitMs: Int = 180,
    itemContent: @Composable (T) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(120.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        itemsIndexed(items, key = { _, it -> key(it) }) { index, item ->
            var visible by remember(key(item)) { mutableStateOf(false) }
            LaunchedEffect(key(item)) {
                delay((baseDelayMs + index * stepDelayMs).toLong())
                visible = true
            }
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(enterMs)) + scaleIn(initialScale = 0.85f, animationSpec = tween(enterMs)),
                exit = fadeOut(tween(exitMs)),
                modifier = Modifier.animateItem(placementSpec = tween(220))
            ) {
                itemContent(item)
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
fun <T> LazyStaggeredGridScope.itemsIndexedPopIn(
    items: List<T>,
    key: (T) -> Any = { it as Any },
    itemContent: @Composable (T) -> Unit
) {
    // Hardcoded timings
    val BASE_DELAY_MS = 60
    val STEP_DELAY_MS = 24
    val ENTER_MS = 260
    val EXIT_MS = 180
    val PLACE_MS = 220
    val INITIAL_SCALE = 0.85f

    itemsIndexed(items, key = { _, it -> key(it) }) { index, item ->
        var visible by remember(key(item)) { mutableStateOf(false) }
        LaunchedEffect(key(item)) {
            delay((BASE_DELAY_MS + index * STEP_DELAY_MS).toLong())
            visible = true
        }
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(ENTER_MS)) +
                    scaleIn(initialScale = INITIAL_SCALE, animationSpec = tween(ENTER_MS)),
            exit = fadeOut(animationSpec = tween(EXIT_MS)),
            modifier = Modifier.animateItem(placementSpec = tween(PLACE_MS))
        ) {
            itemContent(item)
        }
    }
}