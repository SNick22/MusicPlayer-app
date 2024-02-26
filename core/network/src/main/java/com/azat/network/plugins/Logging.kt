package com.azat.network.plugins

import com.azat.network.AndroidLogger
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging

fun HttpClientConfig<*>.logging() {
    install(Logging) {
        level = LogLevel.ALL
        logger = AndroidLogger()
    }
}
