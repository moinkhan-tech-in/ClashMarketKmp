package com.clash.market.di

import com.clash.market.ui.playerdetail.PlayerDetailViewModel
import com.clash.market.ui.screens.HomeViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { PlayerDetailViewModel(get(), get()) }
    single { HomeViewModel(get(), get()) }
}