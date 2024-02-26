package com.azat.player.presentation.mappers

import com.azat.player.presentation.entity.Music
import com.azat.player.presentation.service.PlayerMusic

internal fun PlayerMusic.toMusic(): Music {
    return Music(
        id = this.id,
        title = this.title,
        version = this.version,
        author = this.author,
        duration = this.duration,
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
        author = this.author,
        duration = this.duration,
        audioUrl = this.audioUrl,
        videoUrl = this.videoUrl,
        smallCoverUrl = this.smallCoverUrl,
        largeCoverUrl = this.largeCoverUrl
    )
}
