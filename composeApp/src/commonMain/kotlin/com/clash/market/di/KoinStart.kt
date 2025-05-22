package com.clash.market.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun startSharedKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(appModules)
}