package com.azat.common_impl

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)
