package com.azat.auth.domain.usecase

import com.azat.auth.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ResendCodeUseCaseTest {

    private val userRepository = mock<UserRepository>()
    private val resendCodeUseCase = ResendCodeUseCase(userRepository)

    @Test
    fun `should return Result with Unit when repository returns it`() {
        val testSmsId = 1
        val result = runBlocking {
            Mockito.`when`(userRepository.resendCode(testSmsId)).thenReturn(Result.success(Unit))
            resendCodeUseCase.invoke(testSmsId)
        }
        assertEquals(Unit, result.getOrNull())
    }
}
