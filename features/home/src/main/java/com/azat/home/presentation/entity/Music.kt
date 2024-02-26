package com.azat.home.presentation.entity

import androidx.compose.runtime.Immutable
import kotlin.time.Duration

@Immutable
internal data class Music(
    val id: Int,
    val title: String,
    val version: String?,
    val duration: Duration,
    val author: String,
    val audioUrl: String,
    val videoUrl: String?,
    val smallCoverUrl: String?,
    val largeCoverUrl: String?
)
