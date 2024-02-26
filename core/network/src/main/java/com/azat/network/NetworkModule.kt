package com.azat.network

import com.azat.database.TokenPrefs
import com.azat.network.plugins.authentication
import com.azat.network.plugins.errorMapping
import com.azat.network.plugins.interceptor
import com.azat.network.plugins.logging
import com.azat.network.plugins.serialization
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideKtorClient(tokenPrefs: TokenPrefs): HttpClient {
        return HttpClient(CIO) {
            expectSuccess = true
            logging()
            serialization()
            authentication(tokenPrefs)
            interceptor()
            errorMapping()
        }
    }
}
