package com.azat.auth.presentation.successful_registration.mvi

import com.azat.auth.presentation.AuthNavigator
import com.azat.auth.presentation.AuthScreens
import com.azat.presentation.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SuccessfulRegistrationViewModel @Inject constructor(
    private val authNavigator: AuthNavigator
): MviViewModel<SuccessfulRegistrationEvent>() {

    override fun onEvent(event: SuccessfulRegistrationEvent) {
        when (event) {
            SuccessfulRegistrationEvent.OnNavigateToLogin -> onNavigateToLogin()
        }
    }

    private fun onNavigateToLogin() {
        viewModelScope.launch {
            authNavigator.navigateTo(AuthScreens.Login)
        }
    }
}
