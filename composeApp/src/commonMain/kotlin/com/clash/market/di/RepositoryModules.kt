package com.clash.market.di

import com.clash.market.network.data.repositories.ClanRepository
import com.clash.market.network.data.repositories.ClanRepositoryImpl
import com.clash.market.network.data.repositories.PlayerRepository
import com.clash.market.network.data.repositories.PlayerRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<PlayerRepository> { PlayerRepositoryImpl(get()) }
    single<ClanRepository> { ClanRepositoryImpl(get()) }
}