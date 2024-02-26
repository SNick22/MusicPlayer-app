package com.azat.auth.domain.usecase

import com.azat.auth.domain.entity.RegisterEntity
import com.azat.auth.domain.exceptions.IncorrectPhoneOrPasswordException
import com.azat.auth.domain.exceptions.InvalidPasswordException
import com.azat.auth.domain.exceptions.InvalidPhoneException
import com.azat.auth.domain.exceptions.PasswordsMismatchException
import com.azat.auth.domain.exceptions.UserExistException
import com.azat.auth.domain.repository.UserRepository
import com.azat.auth.domain.validators.PasswordValidator
import com.azat.auth.domain.validators.PhoneNumberValidator
import com.azat.common_impl.UiText
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class RegisterUseCaseTest {

    private val userRepository = mock<UserRepository>()
    private val registerUseCase = RegisterUseCase(
        repository = userRepository,
        phoneNumberValidator = PhoneNumberValidator(),
        passwordValidator = PasswordValidator()
    )

    @Test
    fun `should return Result with Int if passed valid data`() {
        val testRegisterEntity = RegisterEntity(
            phoneNumber = "9603673030",
            password = "12345678",
            passwordRepeat = "12345678",
            notificationsToken = "ghj"
        )
        val testSmsId = 123
        val result = runBlocking {
            Mockito.`when`(
                userRepository.register(testRegisterEntity)
            ).thenReturn(Result.success(testSmsId))
            registerUseCase.invoke(testRegisterEntity)
        }
        assertEquals(testSmsId, result.getOrNull())
    }

    @Test
    fun `should return Result with InvalidPasswordException if passed invalid password`() {
        val testRegisterEntity = RegisterEntity(
            phoneNumber = "9603673030",
            password = "1234567",
            passwordRepeat = "1234567",
            notificationsToken = "ghj"
        )
        val result = runBlocking {
            registerUseCase.invoke(testRegisterEntity)
        }
        assert(result.exceptionOrNull() is InvalidPasswordException)
    }

    @Test
    fun `should return Result with InvalidPhoneException if passed invalid phone`() {
        val testRegisterEntity = RegisterEntity(
            phoneNumber = "fgjfnfgnf",
            password = "12345678",
            passwordRepeat = "12345678",
            notificationsToken = "ghj"
        )
        val result = runBlocking {
            registerUseCase.invoke(testRegisterEntity)
        }
        assert(result.exceptionOrNull() is InvalidPhoneException)
    }

    @Test
    fun `should return Result with PasswordsMismatchException if passwords mismatch`() {
        val testRegisterEntity = RegisterEntity(
            phoneNumber = "9603673030",
            password = "12345678",
            passwordRepeat = "12345677",
            notificationsToken = "ghj"
        )
        val result = runBlocking {
            registerUseCase.invoke(testRegisterEntity)
        }
        assert(result.exceptionOrNull() is PasswordsMismatchException)
    }

    @Test
    fun `should return Result with UserExistException if user exist`() {
        val testRegisterEntity = RegisterEntity(
            phoneNumber = "9603673030",
            password = "12345678",
            passwordRepeat = "12345678",
            notificationsToken = "ghj"
        )
        val result = runBlocking {
            Mockito.`when`(
                userRepository.register(testRegisterEntity)
            ).thenReturn(Result.failure(UserExistException(UiText.DynamicString(""))))
            registerUseCase.invoke(testRegisterEntity)
        }
        assert(result.exceptionOrNull() is UserExistException)
    }
}
