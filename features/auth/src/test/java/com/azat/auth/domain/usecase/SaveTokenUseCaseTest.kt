package com.azat.auth.domain.usecase

import com.azat.database.TokenPrefs
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

class SaveTokenUseCaseTest {

    private val tokenPrefs = mock<TokenPrefs>()
    private val saveTokenUseCase = SaveTokenUseCase(tokenPrefs)

    @Test
    fun `should invoke TokenPrefs putToken() with passed token`() {
        val testToken = "123"
        saveTokenUseCase.invoke(testToken)
        Mockito.verify(tokenPrefs).putToken(testToken)
    }
}
