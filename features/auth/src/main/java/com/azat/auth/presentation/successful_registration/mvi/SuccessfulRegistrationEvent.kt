package com.azat.auth.presentation.successful_registration.mvi

internal sealed interface SuccessfulRegistrationEvent {
    data object OnNavigateToLogin: SuccessfulRegistrationEvent
}
