package com.azat.auth.presentation.registration.mvi

import androidx.compose.runtime.Immutable
import com.azat.common_impl.ValidatableTextFieldState

@Immutable
internal data class RegistrationState(
    val phoneNumber: ValidatableTextFieldState = ValidatableTextFieldState(value = "9603673030"),
    val password: ValidatableTextFieldState = ValidatableTextFieldState(value = "ffffffff"),
    val passwordRepeat: ValidatableTextFieldState = ValidatableTextFieldState(value = "ffffffff"),
    val loading: Boolean = false
)
