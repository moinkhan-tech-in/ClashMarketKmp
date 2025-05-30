package com.clash.market

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.clash.market.di.startSharedKoin
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.screens.clandetail.ClanDetailScreen
import com.clash.market.ui.screens.home.HomeScreen
import com.clash.market.ui.screens.playerdetail.PlayerDetailScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    startSharedKoin()
    ClashNavHost()
}

@Composable
fun ClashNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = ScreenRouts.Home) {

        composable<ScreenRouts.Home> {
            HomeScreen(
                onNavigate = { navController.navigate(it) }
            )
        }

        composable<ScreenRouts.PlayerDetail> { backStackEntry ->
            val player = backStackEntry.toRoute<ScreenRouts.PlayerDetail>()
            PlayerDetailScreen(player)
        }

        composable<ScreenRouts.ClanDetail> { backStackEntry ->
            val clan = backStackEntry.toRoute<ScreenRouts.ClanDetail>()
            ClanDetailScreen(clan)
        }

    }
}