package com.azat.player.presentation.service

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MediaMetadata.MediaType
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.azat.common.Core
import com.azat.player.currentMediaIdAsFlow
import com.azat.player.isPlayingAsFlow
import com.azat.player.playbackPositionAsFlow
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerController @Inject constructor(
    private val mediaControllerFuture: ListenableFuture<MediaController>,
) {

    private var player: Player? = null

    private val playlist: MutableList<PlayerMusic> = mutableListOf()

    private val _playingMusic = MutableStateFlow<PlayerMusic?>(null)
    val playingMusic: StateFlow<PlayerMusic?>
        get() = _playingMusic.asStateFlow()

    private val _playbackPosition = MutableStateFlow(0f)
    val playbackPosition: StateFlow<Float>
        get() = _playbackPosition.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean>
        get() = _isPlaying.asStateFlow()

    init {
        Core.globalScope.launch {
            initPlayer()
            launch {
                collectPlayingMusic()
            }
            launch {
                collectPlaybackPosition()
            }
            launch {
                collectIsPlaying()
            }
        }
    }

    private suspend fun collectIsPlaying() {
        player?.isPlayingAsFlow()?.collect { playing ->
            _isPlaying.emit(playing)
        }
    }

    private suspend fun initPlayer() {
        player = mediaControllerFuture.await()
        player?.prepare()
    }

    private suspend fun collectPlaybackPosition() {
        player?.playbackPositionAsFlow()?.collect { position ->
            _playbackPosition.emit(position)
        }
    }

    private suspend fun collectPlayingMusic() {
        player?.currentMediaIdAsFlow()?.collect { id ->
            _playingMusic.emit(playlist.find { it.id == id })
        }
    }

    fun setMusic(music: PlayerMusic) {
        val mediaMetadata = MediaMetadata.Builder()
            .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
            .setTitle(music.title)
            .setSubtitle(music.version)
            .setArtist(music.author)
            .build()
        val mediaItem = MediaItem.Builder()
            .setMediaId(music.id.toString())
            .setUri(music.audioUrl)
            .setMediaMetadata(mediaMetadata)
            .build()
        playlist.add(music)
        player?.setMediaItem(mediaItem)
    }

    fun play() {
        player?.play()
    }

    fun pause() {
        player?.pause()
    }

    fun seekTo(position: Float) {
        val currentTime = player?.contentDuration
        if (currentTime != null) {
            player?.seekTo((currentTime * position).toLong())
        }
    }
}
