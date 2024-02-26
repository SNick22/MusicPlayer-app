package com.azat.musicplayer.deps_impl.home

fun com.azat.player.presentation.service.PlayerMusic.toHomeMusic(): com.azat.home.PlayerMusic =
    com.azat.home.PlayerMusic(
        id = this.id,
        title = this.title,
        version = this.version,
        author = this.author,
        audioUrl = this.audioUrl,
        videoUrl = this.videoUrl,
        smallCoverUrl = this.smallCoverUrl,
        largeCoverUrl = this.largeCoverUrl,
        duration = this.duration
    )

fun com.azat.home.PlayerMusic.toPlayerMusic(): com.azat.player.presentation.service.PlayerMusic =
    com.azat.player.presentation.service.PlayerMusic(
        id = this.id,
        title = this.title,
        version = this.version,
        author = this.author,
        audioUrl = this.audioUrl,
        videoUrl = this.videoUrl,
        smallCoverUrl = this.smallCoverUrl,
        largeCoverUrl = this.largeCoverUrl,
        duration = this.duration
    )
