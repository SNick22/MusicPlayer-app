package com.azat.auth.domain.usecase

import com.azat.auth.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ResendCodeUseCase @Inject constructor(
    private val repository: UserRepository,
) {

    suspend operator fun invoke(smsId: Int): Result<Unit> {
        return withContext(Dispatchers.IO) {
            repository.resendCode(smsId)
        }
    }
}
