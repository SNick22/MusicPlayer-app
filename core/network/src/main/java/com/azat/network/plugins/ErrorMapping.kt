package com.azat.network.plugins

import com.azat.common.exceptions.NetworkException
import com.azat.common.exceptions.ServerException
import com.azat.common.exceptions.UnauthorizedException
import io.ktor.client.HttpClientConfig
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import java.net.ConnectException

fun HttpClientConfig<*>.errorMapping() {
    HttpResponseValidator {
        handleResponseExceptionWithRequest { exception, _ ->
            when (exception) {
                is ClientRequestException -> {
                    if (exception.response.status == HttpStatusCode.Unauthorized) {
                        throw UnauthorizedException()
                    } else {
                        throw exception
                    }
                }
                is ServerResponseException -> throw ServerException()
                is ConnectTimeoutException -> throw ServerException()
                is ConnectException -> throw NetworkException()
                else -> throw exception
            }
        }
    }
}
