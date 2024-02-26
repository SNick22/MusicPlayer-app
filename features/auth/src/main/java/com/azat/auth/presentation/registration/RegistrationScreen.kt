package com.azat.auth.presentation.registration

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.azat.auth.presentation.registration.mvi.RegistrationViewModel

internal class RegistrationScreen: Screen {

    @Composable
    override fun Content() {
        val viewModel: RegistrationViewModel = getViewModel()
        RegistrationComposeScreen(viewModel = viewModel)
    }
}
