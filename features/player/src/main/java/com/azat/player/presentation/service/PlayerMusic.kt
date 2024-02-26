package com.azat.player.presentation.service

import kotlin.time.Duration

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
