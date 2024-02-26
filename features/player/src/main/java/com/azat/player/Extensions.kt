package com.azat.player

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

internal fun Player.currentMediaIdAsFlow(): Flow<Int?> = callbackFlow {
    trySendBlocking(currentMediaItem?.mediaId?.toInt())
    val listener = object : Player.Listener {
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            trySendBlocking(mediaItem?.mediaId?.toInt())
        }
    }
    addListener(listener)
    awaitClose { removeListener(listener) }
}

internal fun Player.playbackPositionAsFlow(): Flow<Float> = flow {
    while (true) {
        delay(500)
        val duration = contentDuration
        val position = currentPosition.toFloat()
        emit(position / duration)
    }
}

internal fun Player.isPlayingAsFlow(): Flow<Boolean> = callbackFlow {
    trySendBlocking(isPlaying)
    val listener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            trySendBlocking(isPlaying)
        }
    }
    addListener(listener)
    awaitClose { removeListener(listener) }
}

internal fun Player.isReadyAsFlow(): Flow<Boolean> = callbackFlow {
    trySendBlocking(!isLoading)
    val listener = object : Player.Listener {
        override fun onIsLoadingChanged(isLoading: Boolean) {
            trySendBlocking(!isLoading)
        }
    }
    addListener(listener)
    awaitClose { removeListener(listener) }
}
