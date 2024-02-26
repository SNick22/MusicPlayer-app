package com.azat.auth.presentation.registration_confirm.mvi

import androidx.compose.runtime.Immutable

@Immutable
internal data class RegistrationConfirmState(
    val code: String = "",
    val codeLength: Int = 4,
    val timerInitialSeconds: Long = 30L,
    val isResentCodeButtonVisible: Boolean = false,
    val loading: Boolean = false
)
