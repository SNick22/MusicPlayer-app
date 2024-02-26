package com.azat.auth.presentation.login.mvi

internal sealed interface LoginEvent {

    data object OnGoRegistration: LoginEvent
    data object OnLogin: LoginEvent
    data class OnChangePhoneNumber(val value: String): LoginEvent
    data class OnChangePassword(val value: String): LoginEvent
}
