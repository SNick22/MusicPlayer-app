package com.azat.common_impl

data class ValidatableTextFieldState(
    val value: String = "",
    val isError: Boolean = false,
    val validateMessage: UiText? = null
)
