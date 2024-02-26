package com.azat.auth.domain.validators

import com.azat.auth.R
import com.azat.common.isDigitsOnly
import com.azat.common_impl.UiText
import com.azat.common_impl.ValidationResult
import javax.inject.Inject

internal class PhoneNumberValidator @Inject constructor() {

    operator fun invoke(value: String): ValidationResult {
        return if (value.isDigitsOnly() && value.length == 10) {
            ValidationResult(successful = true)
        } else {
            ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.invalid_phone)
            )
        }
    }
}
