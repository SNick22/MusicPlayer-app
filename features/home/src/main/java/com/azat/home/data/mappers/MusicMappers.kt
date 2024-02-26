package com.azat.home.data.mappers

import com.azat.database.entity.MusicEntity
import com.azat.home.data.datasource.music.remote.dto.MusicResponse
import com.azat.network.BuildConfig

internal fun MusicResponse.toMusicEntity(): MusicEntity =
    MusicEntity(
        id = this.id,
        title = this.title,
        version = this.version,
        duration = this.duration,
        author = this.author,
        audio = BuildConfig.BASE_URL + this.audio,
        video = this.video?.let { BuildConfig.BASE_URL + it },
        smallCover = this.smallCover?.let { BuildConfig.BASE_URL + it },
        largeCover = this.largeCover?.let { BuildConfig.BASE_URL + it }
    )
