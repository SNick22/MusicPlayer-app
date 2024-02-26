package com.azat.common

import kotlinx.coroutines.CoroutineScope

interface CoreProvider {

    val toaster: Toaster

    val logger: Logger

    val resources: Resources

    val appRestarter: AppRestarter

    val errorHandler: ErrorHandler

    val globalScope: CoroutineScope
}
