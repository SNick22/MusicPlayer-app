package com.azat.home.presentation.mvi

import com.azat.home.presentation.entity.Music

internal sealed interface HomeEvent {
    data object OnNavigateToSearch: HomeEvent
    data class OnMusicClick(val music: Music): HomeEvent
    data class OnChangeIsPlaying(val value: Boolean): HomeEvent
    data object OnRefreshMusic: HomeEvent
    data object OnOpenPlayer: HomeEvent
}
