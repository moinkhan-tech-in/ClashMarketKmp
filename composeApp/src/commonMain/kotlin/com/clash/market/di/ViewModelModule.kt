package com.clash.market.di

import com.clash.market.ui.screens.dashboard.DashboardViewModel
import com.clash.market.ui.screens.playerdetail.PlayerDetailViewModel
import com.clash.market.ui.screens.home.HomeViewModel
import com.clash.market.ui.screens.search.SearchViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { PlayerDetailViewModel(get(), get()) }
    single { HomeViewModel(get(), get()) }
    single { DashboardViewModel(get(), get(), get()) }
    single { SearchViewModel(get(), get(), get()) }
}