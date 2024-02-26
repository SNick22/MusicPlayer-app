package com.azat.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.azat.common.Core
import com.azat.common.Logger
import com.azat.common.Resources
import com.azat.common.Toaster
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class BaseViewModel: ViewModel() {

    protected val viewModelScope: CoroutineScope by lazy {
        val errorHandler = CoroutineExceptionHandler { _, throwable ->
            Core.errorHandler.handleError(throwable)
        }
        CoroutineScope(SupervisorJob() + Dispatchers.Main + errorHandler)
    }

    protected val globalScope: CoroutineScope get() = Core.globalScope

    protected val resources: Resources get() = Core.resources

    protected val toaster: Toaster get() = Core.toaster

    protected val logger: Logger get() = Core.logger

    init {
        if (BuildConfig.DEBUG) {
            Log.d("ViewModel init", this.toString())
        }
    }

    override fun onCleared() {
        if (BuildConfig.DEBUG) {
            Log.d("ViewModel cleared", this.toString())
        }
        viewModelScope.cancel()
    }
}
