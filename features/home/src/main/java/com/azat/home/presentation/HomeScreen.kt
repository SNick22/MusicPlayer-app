package com.azat.home.presentation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.azat.home.presentation.mvi.HomeViewModel

class HomeScreen: Screen {

    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = getViewModel()
        ComposeHomeScreen(viewModel = viewModel)
    }
}
