package com.clash.market.di

import com.clash.market.network.data.repositories.ClanRepository
import com.clash.market.network.data.repositories.ClanRepositoryImpl
import com.clash.market.network.data.repositories.MetadataRepository
import com.clash.market.network.data.repositories.MetadataRepositoryImpl
import com.clash.market.network.data.repositories.PlayerRepository
import com.clash.market.network.data.repositories.PlayerRepositoryImpl
import com.clash.market.network.data.repositories.RankingRepository
import com.clash.market.network.data.repositories.RankingRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<PlayerRepository> { PlayerRepositoryImpl(get()) }
    single<ClanRepository> { ClanRepositoryImpl(get()) }
    single<MetadataRepository> { MetadataRepositoryImpl(get(), get()) }
    single<RankingRepository> { RankingRepositoryImpl(get()) }
}