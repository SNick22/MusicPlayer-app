package com.azat.auth.domain.usecase

import com.azat.auth.domain.exceptions.IncorrectCodeException
import com.azat.auth.domain.repository.UserRepository
import com.azat.auth.domain.validators.CodeValidator
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.junit.Assert.*

class ConfirmRegisterUseCaseTest {

    private val userRepository = mock<UserRepository>()
    private val confirmRegisterUseCase = ConfirmRegisterUseCase(
        repository = userRepository,
        codeValidator = CodeValidator()
    )

    @Test
    fun `should return Result with Unit if passed valid code`() {
        val testCode = "1234"
        val testSmsId = 1
        val result = runBlocking {
            Mockito.`when`(userRepository.confirmRegister(smsId = testSmsId, code = testCode))
                .thenReturn(Result.success(Unit))
            confirmRegisterUseCase.invoke(smsId = testSmsId, code = testCode)
        }
        assertEquals(Unit, result.getOrNull())
    }

    @Test
    fun `should return Result with IncorrectCodeException if passed not valid code`() {
        val testCode = "qwer"
        val testSmsId = 1
        val result = runBlocking {
            confirmRegisterUseCase.invoke(smsId = testSmsId, code = testCode)
        }
        assert(result.exceptionOrNull() is IncorrectCodeException)
    }

    @Test
    fun `should return Result with IncorrectCodeException if passed valid code but was incorrect`() {
        val testCode = "1234"
        val testSmsId = 1
        val result = runBlocking {
            Mockito.`when`(userRepository.confirmRegister(smsId = testSmsId, code = testCode))
                .thenReturn(Result.failure(IncorrectCodeException()))
            confirmRegisterUseCase.invoke(smsId = testSmsId, code = testCode)
        }
        assert(result.exceptionOrNull() is IncorrectCodeException)
    }
}