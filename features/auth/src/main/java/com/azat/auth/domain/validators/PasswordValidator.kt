package com.azat.auth.domain.validators

import com.azat.auth.R
import com.azat.common_impl.UiText
import com.azat.common_impl.ValidationResult
import javax.inject.Inject

internal class PasswordValidator @Inject constructor() {

    operator fun invoke(value: String): ValidationResult {
        return if (value.length >= 8) {
            ValidationResult(successful = true)
        } else {
            ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.invalid_password)
            )
        }
    }
}
