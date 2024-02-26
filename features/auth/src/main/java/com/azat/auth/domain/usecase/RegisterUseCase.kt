package com.azat.auth.domain.usecase

import com.azat.auth.R
import com.azat.auth.domain.entity.RegisterEntity
import com.azat.auth.domain.exceptions.InvalidPasswordException
import com.azat.auth.domain.exceptions.InvalidPhoneException
import com.azat.auth.domain.exceptions.PasswordsMismatchException
import com.azat.auth.domain.repository.UserRepository
import com.azat.auth.domain.validators.PasswordValidator
import com.azat.auth.domain.validators.PhoneNumberValidator
import com.azat.common_impl.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class RegisterUseCase @Inject constructor(
    private val repository: UserRepository,
    private val phoneNumberValidator: PhoneNumberValidator,
    private val passwordValidator: PasswordValidator
) {

    suspend operator fun invoke(registerEntity: RegisterEntity): Result<Int> {
        return withContext(Dispatchers.IO) {
            val phoneValidation = phoneNumberValidator(registerEntity.phoneNumber)

            if (!phoneValidation.successful) {
                return@withContext Result.failure(
                    InvalidPhoneException(phoneValidation.errorMessage!!)
                )
            }

            val passwordValidation = passwordValidator(registerEntity.password)

            if (!passwordValidation.successful) {
                return@withContext Result.failure(
                    InvalidPasswordException(passwordValidation.errorMessage!!)
                )
            }

            if (registerEntity.password != registerEntity.passwordRepeat) {
                return@withContext Result.failure(
                    PasswordsMismatchException(UiText.StringResource(R.string.passwords_mismatch))
                )
            }

            repository.register(registerEntity)
        }
    }
}
