package com.azat.auth.presentation

import com.azat.navigation.Destination

internal sealed interface AuthScreens: Destination {

    data object Login: AuthScreens
    data object Registration: AuthScreens
    data class RegistrationConfirm(val smsId: Int): AuthScreens
    data object SuccessfulRegistration: AuthScreens
}
