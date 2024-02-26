package com.azat.player.presentation.mvi

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.azat.player.isPlayingAsFlow
import com.azat.player.isReadyAsFlow
import com.azat.player.presentation.entity.Music
import com.azat.player.presentation.mappers.toMusic
import com.azat.player.presentation.service.PlayerController
import com.azat.presentation.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PlayerViewModel @Inject constructor(
    private val playerController: PlayerController,
    val videoPlayer: Player
): MviViewModel<PlayerEvent>() {

    private val _screenState = MutableStateFlow(PlayerState())
    val screenState: StateFlow<PlayerState>
        get() = _screenState.asStateFlow()

    init {
        initVideoPlayer()
        viewModelScope.launch {
            launch {
                collectPlayingMusic()
            }
            launch {
                collectVideoIsReady()
            }
            launch {
                collectPlayProgress()
            }
            launch {
                collectIsPlaying()
            }
        }
    }

    private suspend fun collectIsPlaying() {
        playerController.isPlaying.collect { isPlaying ->
            _screenState.value = screenState.value.copy(isPaused = !isPlaying)
        }
    }

    private suspend fun collectPlayProgress() {
        playerController.playbackPosition.collect { position ->
            _screenState.value = screenState.value.copy(playProgress = position)
        }
    }

    private fun initVideoPlayer() {
        videoPlayer.prepare()
        videoPlayer.repeatMode = Player.REPEAT_MODE_ALL
        videoPlayer.playWhenReady = true
    }

    private suspend fun collectPlayingMusic() {
        playerController.playingMusic.collect { music ->
            _screenState.value = screenState.value.copy(playingMusic = music?.toMusic())
            if (music?.videoUrl != null) {
                setVideo(music.videoUrl)
            }
        }
    }

    private fun setVideo(videoUrl: String) {
        val mediaItem = MediaItem.Builder()
            .setMediaId(videoUrl)
            .setUri(videoUrl)
            .build()
        videoPlayer.setMediaItem(mediaItem)
    }

    private suspend fun collectVideoIsReady() {
        videoPlayer.isPlayingAsFlow().collect { isPlaying ->
            _screenState.value = screenState.value.copy(isVideoReady = isPlaying)
        }
    }

    override fun onCleared() {
        videoPlayer.release()
        super.onCleared()
    }

    override fun onEvent(event: PlayerEvent) {
        when (event) {
            PlayerEvent.OnPause -> onPause()
            PlayerEvent.OnPlay -> onPlay()
            is PlayerEvent.OnMoveSlider -> onMoveSlider(event.position)
        }
    }

    private fun onMoveSlider(position: Float) {
        playerController.seekTo(position)
    }

    private fun onPause() {
        playerController.pause()
        videoPlayer.pause()
    }

    private fun onPlay() {
        playerController.play()
        videoPlayer.play()
    }
}
