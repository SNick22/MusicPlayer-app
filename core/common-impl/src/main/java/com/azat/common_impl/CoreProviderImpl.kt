package com.azat.common_impl

import android.content.Context
import com.azat.common.AppRestarter
import com.azat.common.CoreProvider
import com.azat.common.ErrorHandler
import com.azat.common.Logger
import com.azat.common.Resources
import com.azat.common.Toaster
import kotlinx.coroutines.CoroutineScope

class CoreProviderImpl(
    private val appContext: Context,
    override val toaster: Toaster = AndroidToaster(appContext),
    override val logger: Logger = AndroidLogger(),
    override val resources: Resources = AndroidResources(appContext),
    override val appRestarter: AppRestarter,
    override val errorHandler: ErrorHandler = RootErrorHandler(
        logger = logger,
        toaster = toaster,
        appRestarter = appRestarter,
        resources = resources
    ),
    override val globalScope: CoroutineScope = createGlobalScope()
): CoreProvider
