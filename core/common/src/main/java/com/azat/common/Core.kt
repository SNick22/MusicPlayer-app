package com.azat.common

import kotlinx.coroutines.CoroutineScope

object Core {

    private lateinit var coreProvider: CoreProvider

    val toaster: Toaster get() = coreProvider.toaster

    val resources: Resources get() = coreProvider.resources

    val logger: Logger get() = coreProvider.logger

    val globalScope: CoroutineScope get() = coreProvider.globalScope

    val errorHandler: ErrorHandler get() = coreProvider.errorHandler

    val appRestarter: AppRestarter get() = coreProvider.appRestarter

    fun init(coreProvider: CoreProvider) {
        this.coreProvider = coreProvider
    }
}
