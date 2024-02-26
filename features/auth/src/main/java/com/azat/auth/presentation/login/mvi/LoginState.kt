package com.azat.auth.presentation.login.mvi

import androidx.compose.runtime.Immutable
import com.azat.common_impl.ValidatableTextFieldState

@Immutable
internal data class LoginState(
    val phoneNumber: ValidatableTextFieldState = ValidatableTextFieldState(value = "9603673030"),
    val password: ValidatableTextFieldState = ValidatableTextFieldState(value = "ffffffff"),
    val loading: Boolean = false
)
