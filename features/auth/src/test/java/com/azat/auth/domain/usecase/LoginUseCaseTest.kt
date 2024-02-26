package com.azat.auth.domain.usecase

import com.azat.auth.domain.exceptions.IncorrectPhoneOrPasswordException
import com.azat.auth.domain.exceptions.InvalidPasswordException
import com.azat.auth.domain.exceptions.InvalidPhoneException
import com.azat.auth.domain.repository.UserRepository
import com.azat.auth.domain.validators.PasswordValidator
import com.azat.auth.domain.validators.PhoneNumberValidator
import com.azat.common_impl.UiText
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class LoginUseCaseTest {

    private val userRepository = mock<UserRepository>()
    private val loginUseCase = LoginUseCase(
        repository = userRepository,
        phoneNumberValidator = PhoneNumberValidator(),
        passwordValidator = PasswordValidator()
    )

    @Test
    fun `should return Result with String if passed valid phone and password`() {
        val testPhoneNumber = "9173864547"
        val testPassword = "12345678"
        val testToken = "123"
        val result = runBlocking {
            Mockito.`when`(
                userRepository.login(phoneNumber = testPhoneNumber, password = testPassword)
            ).thenReturn(Result.success(testToken))
            loginUseCase.invoke(phoneNumber = testPhoneNumber, password = testPassword)
        }
        assertEquals(testToken, result.getOrNull())
    }

    @Test
    fun `should return Result with InvalidPasswordException if passed invalid password`() {
        val testPhoneNumber = "9173864547"
        val testPassword = "1234567"
        val result = runBlocking {
            loginUseCase.invoke(phoneNumber = testPhoneNumber, password = testPassword)
        }
        assert(result.exceptionOrNull() is InvalidPasswordException)
    }

    @Test
    fun `should return Result with InvalidPhoneException if passed invalid phone`() {
        val testPhoneNumber = "917386454g"
        val testPassword = "12345678"
        val result = runBlocking {
            loginUseCase.invoke(phoneNumber = testPhoneNumber, password = testPassword)
        }
        assert(result.exceptionOrNull() is InvalidPhoneException)
    }

    @Test
    fun `should return Result with IncorrectPhoneOrPasswordException if login error`() {
        val testPhoneNumber = "9173864545"
        val testPassword = "12345678"
        val result = runBlocking {
            Mockito.`when`(
                userRepository.login(phoneNumber = testPhoneNumber, password = testPassword)
            ).thenReturn(Result.failure(IncorrectPhoneOrPasswordException(UiText.DynamicString(""))))
            loginUseCase.invoke(phoneNumber = testPhoneNumber, password = testPassword)
        }
        assert(result.exceptionOrNull() is IncorrectPhoneOrPasswordException)
    }
}
