package com.azat.auth.domain.validators

import com.azat.common.isDigitsOnly
import com.azat.common_impl.ValidationResult
import javax.inject.Inject

internal class CodeValidator @Inject constructor() {

    operator fun invoke(value: String): ValidationResult {
        return if (value.isDigitsOnly() && value.length == 4) {
            ValidationResult(successful = true)
        } else {
            ValidationResult(successful = false)
        }
    }
}
