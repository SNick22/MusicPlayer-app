package com.azat.auth.presentation.registration_confirm.mvi

internal sealed interface RegistrationConfirmEvent {

    data class OnChangeCode(val value: String): RegistrationConfirmEvent
    data class OnCodeEntered(val smsId: Int): RegistrationConfirmEvent
    data class OnChangeResentCodeButtonVisibility(val visible: Boolean): RegistrationConfirmEvent
    data class OnResentCode(val smsId: Int): RegistrationConfirmEvent
}
