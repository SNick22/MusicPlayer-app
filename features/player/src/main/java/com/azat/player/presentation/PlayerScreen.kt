package com.azat.player.presentation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.azat.player.presentation.mvi.PlayerViewModel

class PlayerScreen: Screen {

    @Composable
    override fun Content() {
        val viewModel: PlayerViewModel = getViewModel()
        PlayerComposeScreen(viewModel = viewModel)
    }
}
