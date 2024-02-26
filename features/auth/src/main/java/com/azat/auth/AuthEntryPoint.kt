package com.azat.auth

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.azat.auth.presentation.login.LoginScreen
import com.azat.auth.presentation.AuthNavigationEffectHolder
import com.azat.auth.presentation.AuthScreens
import com.azat.auth.presentation.registration.RegistrationScreen
import com.azat.auth.presentation.registration_confirm.RegistrationConfirmScreen
import com.azat.auth.presentation.successful_registration.SuccessfulRegistrationScreen
import com.azat.navigation.NavigationIntent
import com.azat.navigation.VoyagerNavigationContainer
import kotlinx.coroutines.flow.SharedFlow

class AuthEntryPoint: VoyagerNavigationContainer() {

    override val startScreen: Screen = LoginScreen()

    @Composable
    override fun getNavigationEffect(): SharedFlow<NavigationIntent> {
        val effectHolder: AuthNavigationEffectHolder = getViewModel()
        return effectHolder.navigationFlow
    }

    @Composable
    override fun NavigationWrapper(navigator: Navigator, content: @Composable () -> Unit) {
        SlideTransition(navigator)
    }

    override fun navigate(navigator: Navigator, intent: NavigationIntent) {
        when (intent) {
            NavigationIntent.NavigateBack -> navigator.pop()
            is NavigationIntent.NavigateTo -> {
                when (val screen = intent.screen as AuthScreens) {
                    AuthScreens.Login -> navigator.push(LoginScreen())
                    AuthScreens.Registration -> navigator.push(RegistrationScreen())
                    is AuthScreens.RegistrationConfirm ->
                        navigator.push(RegistrationConfirmScreen(screen.smsId))
                    is AuthScreens.SuccessfulRegistration ->
                        navigator.replace(SuccessfulRegistrationScreen())
                }
            }
        }
    }
}
