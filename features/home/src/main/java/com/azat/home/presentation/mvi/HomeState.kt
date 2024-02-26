package com.azat.home.presentation.mvi

import androidx.compose.runtime.Immutable
import com.azat.home.presentation.entity.Music
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class HomeState(
    val nowPlayingMusic: Music? = null,
    val isPlaying: Boolean = true,
    val playProgress: Float = 0f
)
