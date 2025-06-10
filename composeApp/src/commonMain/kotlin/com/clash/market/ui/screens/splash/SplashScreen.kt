package com.clash.market.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_main_logo
import org.jetbrains.compose.resources.painterResource
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = getKoin().get<SplashViewModel>(),
    onReady: () -> Unit
) {
    val isReady = viewModel.isReady.collectAsStateWithLifecycle()

    LaunchedEffect(isReady.value) {
        if (isReady.value) onReady()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.size(240.dp),
            painter = painterResource(Res.drawable.ic_main_logo),
            contentDescription = null
        )
        LinearProgressIndicator(
            modifier = Modifier
                .padding(top = 160.dp)
                .width(200.dp),
            strokeCap = StrokeCap.Round,
            color = MaterialTheme.colorScheme.onPrimary,
            backgroundColor = MaterialTheme.colorScheme.primary
        )
    }
}