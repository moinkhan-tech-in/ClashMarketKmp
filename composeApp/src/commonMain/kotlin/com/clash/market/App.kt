package com.clash.market

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.clash.market.di.startSharedKoin
import com.clash.market.navigation.ScreenRouts
import com.clash.market.theme.ClashDarkColors
import com.clash.market.theme.ClashLightColors
import com.clash.market.theme.ClashTypography
import com.clash.market.ui.screens.clandetail.ClanDetailScreen
import com.clash.market.ui.screens.home.HomeScreen
import com.clash.market.ui.screens.playerdetail.PlayerDetailScreen
import com.clash.market.ui.screens.splash.SplashScreen
import com.clash.market.ui.screens.warlogs.WarLogsScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    startSharedKoin()
    ClashTheme { ClashNavHost() }
}

@Composable
fun ClashTheme(
    useDarkTheme: Boolean = false, //isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) ClashDarkColors else ClashLightColors
    MaterialTheme(
        colorScheme = colors,
        typography = ClashTypography,
        shapes = MaterialTheme.shapes.copy(),
        content = content
    )
}

@Composable
fun ClashNavHost() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = ScreenRouts.Home) {

        composable<ScreenRouts.Splash> {
            SplashScreen(
                onReady = {
                    navController.navigate(ScreenRouts.Home) {
                        popUpTo(ScreenRouts.Splash) { inclusive = true }
                    }
                }
            )
        }

        composable<ScreenRouts.Home> {
            HomeScreen(onNavigate = { navController.navigate(it) })
        }

        composable<ScreenRouts.PlayerDetail> { backStackEntry ->
            val player = backStackEntry.toRoute<ScreenRouts.PlayerDetail>()
            PlayerDetailScreen(
                playerDetailRoute = player,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<ScreenRouts.ClanDetail> { backStackEntry ->
            val clan = backStackEntry.toRoute<ScreenRouts.ClanDetail>()
            ClanDetailScreen(
                clanDetailRoute = clan,
                onNavigate = { navController.navigate(it) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<ScreenRouts.WarLogs> { backStackEntry ->
            val warLogs = backStackEntry.toRoute<ScreenRouts.WarLogs>()
            WarLogsScreen(
                warLogsRoute = warLogs,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}