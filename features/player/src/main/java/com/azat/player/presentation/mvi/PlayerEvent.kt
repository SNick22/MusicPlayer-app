package com.azat.player.presentation.mvi

internal sealed interface PlayerEvent {
    data object OnPlay:  PlayerEvent
    data object OnPause: PlayerEvent
    data class OnMoveSlider(val position: Float): PlayerEvent
}
