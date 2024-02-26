package com.azat.auth.domain.usecase

import com.azat.database.TokenPrefs
import javax.inject.Inject

internal class SaveTokenUseCase @Inject constructor(
    private val tokenPrefs: TokenPrefs
) {

    operator fun invoke(token: String) {
        tokenPrefs.putToken(token)
    }
}
