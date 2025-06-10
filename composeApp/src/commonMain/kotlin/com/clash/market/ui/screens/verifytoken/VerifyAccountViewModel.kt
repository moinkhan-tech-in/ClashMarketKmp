package com.clash.market.ui.screens.verifytoken

import com.clash.market.base.BaseViewModel
import com.clash.market.network.data.repositories.PlayerRepository


class VerifyAccountViewModel(
    val playerRepository: PlayerRepository
) : BaseViewModel() {

    fun onTokenSubmit(tag: String, token: String) {
        launchIO {
            val response = playerRepository.verifyToken(tag, token)

        }
    }
}

