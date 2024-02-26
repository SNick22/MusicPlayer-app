package com.azat.auth.presentation.successful_registration

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.azat.auth.presentation.successful_registration.mvi.SuccessfulRegistrationViewModel

class SuccessfulRegistrationScreen: Screen {

    @Composable
    override fun Content() {
        val viewModel: SuccessfulRegistrationViewModel = getViewModel()
        SuccessfulRegistrationComposeScreen(viewModel = viewModel)
    }
}
