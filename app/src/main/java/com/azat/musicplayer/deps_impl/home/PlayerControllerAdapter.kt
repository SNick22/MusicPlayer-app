package com.azat.musicplayer.deps_impl.home

import com.azat.home.PlayerConnection
import com.azat.home.PlayerMusic
import com.azat.player.presentation.service.PlayerController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class PlayerControllerAdapter @Inject constructor(
    private val realPlayerController: PlayerController
): PlayerConnection {

    override val playingMusic: Flow<PlayerMusic>
        get() = realPlayerController.playingMusic.mapNotNull { it?.toHomeMusic() }

    override val playbackPosition: Flow<Float>
        get() = realPlayerController.playbackPosition

    override val isPlaying: Flow<Boolean>
        get() = realPlayerController.isPlaying

    override fun setMusic(music: PlayerMusic) {
        realPlayerController.setMusic(music.toPlayerMusic())
    }

    override fun play() {
        realPlayerController.play()
    }

    override fun pause() {
        realPlayerController.pause()
    }
}
