package com.azat.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

@Composable
fun CountDownTimer(
    initialTimeSeconds: Long,
    intervalSeconds: Long,
    onFinishTimer: () -> Unit,
    content: @Composable (Long) -> Unit
) {

    var currentTime by rememberSaveable(initialTimeSeconds) {
        mutableLongStateOf(initialTimeSeconds)
    }

    LaunchedEffect(Unit) {
        while (currentTime > 0) {
            delay(intervalSeconds * 1000L)
            currentTime -= intervalSeconds
        }
        onFinishTimer()
    }

    content(currentTime)
}
