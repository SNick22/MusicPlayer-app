package com.azat.home.presentation.mappers

import com.azat.database.entity.MusicEntity
import com.azat.home.PlayerMusic
import com.azat.home.presentation.entity.Music
import kotlin.time.Duration.Companion.seconds

internal fun MusicEntity.toMusic(): Music {
    return Music(
        id = this.id,
        title = this.title,
        version = this.version,
        duration = this.duration.seconds,
        author = this.author,
        audioUrl = this.audio,
        videoUrl = this.video,
        smallCoverUrl = this.smallCover,
        largeCoverUrl = this.largeCover
    )
}

internal fun PlayerMusic.toMusic(): Music {
    return Music(
        id = this.id,
        title = this.title,
        version = this.version,
        duration = this.duration,
        author = this.author,
        audioUrl = this.audioUrl,
        videoUrl = this.videoUrl,
        smallCoverUrl = this.smallCoverUrl,
        largeCoverUrl = this.largeCoverUrl
    )
}

internal fun Music.toPlayerMusic(): PlayerMusic {
    return PlayerMusic(
        id = this.id,
        title = this.title,
        version = this.version,
        duration = this.duration,
        author = this.author,
        audioUrl = this.audioUrl,
        videoUrl = this.videoUrl,
        smallCoverUrl = this.smallCoverUrl,
        largeCoverUrl = this.largeCoverUrl
    )
}
