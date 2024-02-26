package com.azat.player.presentation.entity

import javax.annotation.concurrent.Immutable
import kotlin.time.Duration

@Immutable
internal data class Music(
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
