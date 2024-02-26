package com.azat.network.plugins

import com.azat.network.BuildConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType

fun HttpClientConfig<*>.interceptor() {
    defaultRequest {
        url(BuildConfig.BASE_URL)
        contentType(ContentType.Application.Json)
    }
}
