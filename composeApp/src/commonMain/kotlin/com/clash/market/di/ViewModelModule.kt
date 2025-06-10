package com.clash.market.di

import com.clash.market.ui.contents.clandetail.ClanDetailContentViewModel
import com.clash.market.ui.contents.playerdetail.PlayerDetailContentViewModel
import com.clash.market.ui.screens.dashboard.DashboardViewModel
import com.clash.market.ui.screens.home.HomeViewModel
import com.clash.market.ui.screens.search.SearchViewModel
import com.clash.market.ui.screens.splash.SplashViewModel
import com.clash.market.ui.screens.warlogs.WarLogsViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { SplashViewModel(get()) }
    single { HomeViewModel(get()) }
    single { DashboardViewModel(get(), get(), get(), get()) }
    single { SearchViewModel(get()) }
    single { ClanDetailContentViewModel(get()) }
    single { PlayerDetailContentViewModel(get()) }
    single { WarLogsViewModel(get()) }
}