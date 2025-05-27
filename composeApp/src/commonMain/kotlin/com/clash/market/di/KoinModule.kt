package com.clash.market.di

import com.clash.market.network.httpClient
import org.koin.core.module.Module
import org.koin.dsl.module

val sharedModule = module {
    single { httpClient }
}

val appModules: List<Module> = listOf(
    preferenceModule,
    sharedModule,
    repositoryModule,
    viewModelModule
)