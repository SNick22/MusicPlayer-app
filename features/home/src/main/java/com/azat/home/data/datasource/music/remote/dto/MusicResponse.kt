package com.azat.home.data.datasource.music.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MusicResponse(
    val id: Int,
    val title: String,
    val version: String?,
    val duration: Long,
    val author: String,
    val audio: String,
    val video: String?,
    val smallCover: String?,
    val largeCover: String?
)
