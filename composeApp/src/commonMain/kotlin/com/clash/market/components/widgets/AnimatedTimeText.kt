package com.clash.market.components.widgets

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

@Composable
fun AnimatedTimeText(remainTime: String) {
    var previous by remember { mutableStateOf(remainTime) }

    LaunchedEffect(remainTime) { previous = remainTime }

    Row(verticalAlignment = Alignment.CenterVertically) {
        remainTime.forEachIndexed { index, newChar ->
            val oldChar = previous.getOrNull(index)

            AnimatedContent(
                targetState = newChar,
                transitionSpec = {
                    if (oldChar != null && oldChar != newChar)
                        (slideInVertically { height -> height } + fadeIn()).togetherWith(
                            slideOutVertically { height -> -height } + fadeOut()
                        )
                    else
                        EnterTransition.None togetherWith ExitTransition.None
                },
                label = "char-$index"
            ) { char ->
                Text(
                    text = char.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}