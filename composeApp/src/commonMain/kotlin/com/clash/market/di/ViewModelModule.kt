package com.clash.market.di

import com.clash.market.ui.playerdetail.PlayerDetailViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { PlayerDetailViewModel(get()) }
}