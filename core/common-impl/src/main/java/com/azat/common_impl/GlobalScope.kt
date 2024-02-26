package com.azat.common_impl

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

fun createGlobalScope(): CoroutineScope {
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("GlobalScope", "Error", throwable)
    }
    return CoroutineScope(SupervisorJob() + Dispatchers.Main + coroutineExceptionHandler)
}