package com.azat.home.presentation.mvi

import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.azat.database.entity.MusicEntity
import com.azat.home.HomeFeatureNavigator
import com.azat.home.PlayerConnection
import com.azat.home.presentation.entity.Music
import com.azat.home.presentation.mappers.toMusic
import com.azat.home.presentation.mappers.toPlayerMusic
import com.azat.presentation.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    pager: Pager<Int, MusicEntity>,
    private val playerController: PlayerConnection,
    private val homeFeatureNavigator: HomeFeatureNavigator
): MviViewModel<HomeEvent>() {

    private val _screenState = MutableStateFlow(HomeState())
    val screenState: StateFlow<HomeState>
        get() = _screenState.asStateFlow()

    val musicPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toMusic() }
        }
        .cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            launch {
                collectCurrentPlaying()
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
        playerController.isPlaying.collect { playing ->
            _screenState.value = screenState.value.copy(isPlaying = playing)
        }
    }

    private suspend fun collectPlaybackPosition() {
        playerController.playbackPosition.collect { position ->
            _screenState.value = screenState.value.copy(playProgress = position)
        }
    }

    private suspend fun collectCurrentPlaying() {
        playerController.playingMusic.collect { playingMusic ->
            _screenState.value = screenState.value.copy(
                nowPlayingMusic = playingMusic.toMusic()
            )
        }
    }

    override fun onEvent(event: HomeEvent) {
        when(event) {
            HomeEvent.OnNavigateToSearch -> onNavigateToSearch()
            is HomeEvent.OnMusicClick -> onMusicClick(event.music)
            is HomeEvent.OnChangeIsPlaying -> onChangeIsPlaying(event.value)
            HomeEvent.OnRefreshMusic -> onRefreshMusic()
            HomeEvent.OnOpenPlayer -> onOpenPlayer()
        }
    }

    private fun onOpenPlayer() {
        viewModelScope.launch {
            homeFeatureNavigator.openPlayer()
        }
    }

    private fun onRefreshMusic() {

    }

    private fun onChangeIsPlaying(value: Boolean) {
        if (value) {
            playerController.play()
        } else {
            playerController.pause()
        }
    }

    private fun onNavigateToSearch() {

    }

    private fun onMusicClick(music: Music) {
        if (screenState.value.nowPlayingMusic != music) {
            playerController.setMusic(music.toPlayerMusic())
            playerController.play()
        } else {
            if (screenState.value.isPlaying) {
                playerController.pause()
            } else {
                playerController.play()
            }
        }
    }
}
