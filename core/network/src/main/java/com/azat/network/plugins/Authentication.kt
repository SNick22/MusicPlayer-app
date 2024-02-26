package com.azat.network.plugins

import com.azat.database.TokenPrefs
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer

fun HttpClientConfig<*>.authentication(tokenPrefs: TokenPrefs) {
    install(Auth) {
        bearer {
            loadTokens {
                tokenPrefs.getToken()?.let {
                    BearerTokens(it, it)
                }
            }
        }
    }
}
