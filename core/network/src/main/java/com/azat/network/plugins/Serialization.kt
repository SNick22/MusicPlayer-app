package com.azat.network.plugins

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

fun HttpClientConfig<*>.serialization() {
    install(ContentNegotiation) {
        json()
    }
}
