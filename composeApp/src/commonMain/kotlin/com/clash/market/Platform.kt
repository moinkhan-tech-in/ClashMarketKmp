package com.clash.market

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform