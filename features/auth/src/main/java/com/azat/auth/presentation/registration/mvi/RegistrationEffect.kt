package com.azat.auth.presentation.registration.mvi

import com.azat.common_impl.UiText

internal sealed interface RegistrationEffect {
    data class ShowToast(val text: UiText): RegistrationEffect
}
