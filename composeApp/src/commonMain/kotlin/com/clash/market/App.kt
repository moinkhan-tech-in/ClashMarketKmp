package com.clash.market

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.clash.market.di.startSharedKoin
import com.clash.market.ui.playerdetail.PlayerDetailScreen
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    startSharedKoin()
    ClashNavHost()
}

@Composable
fun ClashNavHost() {
    NavHost(rememberNavController(), startDestination = PlayerDetailRoute("#2GYCPJJY2")) {
        composable<PlayerDetailRoute> { backStackEntry ->
            val player = backStackEntry.toRoute<PlayerDetailRoute>()
            PlayerDetailScreen(player)
        }

    }
}

@Serializable
data class PlayerDetailRoute(
    val tag: String
)