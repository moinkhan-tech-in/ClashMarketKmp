package com.clash.market.di

import com.clash.market.ui.contents.clandetail.ClanDetailContentViewModel
import com.clash.market.ui.contents.playerdetail.PlayerDetailContentViewModel
import com.clash.market.ui.screens.dashboard.DashboardViewModel
import com.clash.market.ui.screens.home.HomeViewModel
import com.clash.market.ui.screens.rankings.RankingsViewModel
import com.clash.market.ui.screens.search.SearchViewModel
import com.clash.market.ui.screens.splash.SplashViewModel
import com.clash.market.ui.screens.warlogs.WarLogsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::DashboardViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::ClanDetailContentViewModel)
    viewModelOf(::PlayerDetailContentViewModel)
    viewModelOf(::WarLogsViewModel)
    viewModelOf(::RankingsViewModel)
}