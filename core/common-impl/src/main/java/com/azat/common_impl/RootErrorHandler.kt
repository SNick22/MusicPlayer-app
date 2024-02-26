package com.azat.common_impl

import com.azat.common.AppRestarter
import com.azat.common.ErrorHandler
import com.azat.common.Logger
import com.azat.common.Resources
import com.azat.common.Toaster
import com.azat.common.exceptions.NetworkException
import com.azat.common.exceptions.ServerException
import com.azat.common.exceptions.UnauthorizedException
import kotlinx.coroutines.CancellationException

class RootErrorHandler(
    private val logger: Logger,
    private val toaster: Toaster,
    private val appRestarter: AppRestarter,
    private val resources: Resources
): ErrorHandler {

    override fun handleError(exception: Throwable) {
        logger.err(exception)
        when (exception) {
            is ServerException -> handleServerException(exception)
            is NetworkException -> handleNetworkException(exception)
            is UnauthorizedException -> handleUnauthorizedException()
            is CancellationException -> Unit
            else -> handleUnknownException(exception)
        }
    }

    private fun handleUnauthorizedException() {
        appRestarter.restartApp()
    }

    private fun handleUnknownException(exception: Throwable) {
        val message = getUserMessage(exception)
        toaster.showToast(message)
    }

    private fun handleNetworkException(exception: NetworkException) {
        val message = getUserMessage(exception)
        toaster.showToast(message)
    }

    private fun handleServerException(exception: ServerException) {
        val message = getUserMessage(exception)
        toaster.showToast(message)
    }

    override fun getUserMessage(exception: Throwable): String =
        when (exception) {
            is ServerException -> resources.getString(R.string.server_error_message)
            is NetworkException -> resources.getString(R.string.network_error_message)
            else -> resources.getString(R.string.unknown_error_message)
        }
}
