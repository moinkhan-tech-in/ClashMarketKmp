package com.clash.market.base

sealed interface ResultState<out T> {
    object Ideal : ResultState<Nothing>
    object Loading : ResultState<Nothing>
    data class Success<T>(val data: T) : ResultState<T>
    data class Error(val message: String?, val throwable: Throwable? = null) : ResultState<Nothing>
}