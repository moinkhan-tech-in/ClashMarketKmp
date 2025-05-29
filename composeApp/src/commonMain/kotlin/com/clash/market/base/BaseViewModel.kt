package com.clash.market.base

import com.clash.market.ioDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


open class BaseViewModel {
    protected val viewModelScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)

    open fun onCleared() {
        viewModelScope.cancel()
    }

    fun launchIO(block: suspend () -> Unit) {
        viewModelScope.launch(ioDispatcher) { block() }
    }
}