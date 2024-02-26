package com.azat.auth.presentation.login

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.azat.auth.presentation.login.mvi.LoginViewModel

internal class LoginScreen: Screen {

    @Composable
    override fun Content() {
        val viewModel: LoginViewModel = getViewModel()
        LoginComposeScreen(viewModel = viewModel)
    }
}
