package com.azat.auth.domain.usecase

import com.azat.auth.domain.exceptions.IncorrectCodeException
import com.azat.auth.domain.repository.UserRepository
import com.azat.auth.domain.validators.CodeValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ConfirmRegisterUseCase @Inject constructor(
    private val repository: UserRepository,
    private val codeValidator: CodeValidator
) {

    suspend operator fun invoke(smsId: Int, code: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            val codeValidation = codeValidator(code)

            if (!codeValidation.successful) {
                return@withContext Result.failure(IncorrectCodeException())
            }

            repository.confirmRegister(smsId = smsId, code = code)
        }
    }
}
