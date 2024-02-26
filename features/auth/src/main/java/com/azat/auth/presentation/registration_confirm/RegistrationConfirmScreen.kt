package com.azat.auth.presentation.registration_confirm

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.azat.auth.presentation.registration_confirm.mvi.RegistrationConfirmViewModel

internal data class RegistrationConfirmScreen(val smsId: Int): Screen {

    @Composable
    override fun Content() {
        val viewModel: RegistrationConfirmViewModel = getViewModel()
        RegistrationConfirmComposeScreen(smsId = smsId, viewModel = viewModel)
    }
}
