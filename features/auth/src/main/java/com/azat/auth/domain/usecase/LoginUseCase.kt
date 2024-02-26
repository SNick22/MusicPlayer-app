package com.azat.auth.domain.usecase

import com.azat.auth.domain.exceptions.InvalidPasswordException
import com.azat.auth.domain.exceptions.InvalidPhoneException
import com.azat.auth.domain.repository.UserRepository
import com.azat.auth.domain.validators.PasswordValidator
import com.azat.auth.domain.validators.PhoneNumberValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class LoginUseCase @Inject constructor(
    private val repository: UserRepository,
    private val phoneNumberValidator: PhoneNumberValidator,
    private val passwordValidator: PasswordValidator
) {

    suspend operator fun invoke(phoneNumber: String, password: String): Result<String> {
        return withContext(Dispatchers.IO) {
            val phoneValidation = phoneNumberValidator(phoneNumber)

            if (!phoneValidation.successful) {
                return@withContext Result.failure(
                    InvalidPhoneException(phoneValidation.errorMessage!!)
                )
            }

            val passwordValidation = passwordValidator(password)

            if (!passwordValidation.successful) {
                return@withContext Result.failure(
                    InvalidPasswordException(passwordValidation.errorMessage!!)
                )
            }

            repository.login(phoneNumber = phoneNumber, password = password)
        }
    }
}
