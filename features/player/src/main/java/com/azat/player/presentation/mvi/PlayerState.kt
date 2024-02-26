package com.azat.player.presentation.mvi

import com.azat.player.presentation.entity.Music
import javax.annotation.concurrent.Immutable

@Immutable
internal data class PlayerState(
    val playingMusic: Music? = null,
    val isPaused: Boolean = false,
    val playProgress: Float = 0f,
    val isVideoReady: Boolean = false
)
