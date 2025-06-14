package com.clash.market.base

import androidx.lifecycle.ViewModel
import com.clash.market.ioDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {
    protected val viewModelScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun launchIO(block: suspend () -> Unit) {
        viewModelScope.launch(ioDispatcher) { block() }
    }
}