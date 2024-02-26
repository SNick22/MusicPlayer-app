package com.azat.auth.presentation.registration.mvi

internal sealed interface RegistrationEvent {

    data object OnRegister: RegistrationEvent
    data class OnChangePhoneNumber(val value: String): RegistrationEvent
    data class OnChangePassword(val value: String): RegistrationEvent
    data class OnChangePasswordRepeat(val value: String): RegistrationEvent
}
