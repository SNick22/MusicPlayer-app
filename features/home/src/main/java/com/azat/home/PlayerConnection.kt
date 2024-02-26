package com.azat.home

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface PlayerConnection {

    val playingMusic: Flow<PlayerMusic>

    val playbackPosition: Flow<Float>

    val isPlaying: Flow<Boolean>

    fun setMusic(music: PlayerMusic)

    fun play()

    fun pause()
}

data class PlayerMusic(
    val id: Int,
    val title: String,
    val version: String?,
    val author: String,
    val duration: Duration,
    val audioUrl: String,
    val videoUrl: String?,
    val smallCoverUrl: String?,
    val largeCoverUrl: String?,
)
