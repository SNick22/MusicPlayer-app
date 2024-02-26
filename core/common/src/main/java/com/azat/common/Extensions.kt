package com.azat.common

import kotlin.time.Duration

fun Duration.formatToMinutesSeconds(): String {
    return this.toComponents { minutes, seconds, _ ->
        String.format("%02d:%02d", minutes, seconds)
    }
}

inline fun <R, T>Result<R>.flatMap(transform: (R) -> Result<T>): Result<T> =
    fold(
        onSuccess = { transform(it) },
        onFailure = { Result.failure(it) }
    )

fun String.isDigitsOnly(): Boolean {
    this.forEach { char ->
        if (!char.isDigit()) {
            return false
        }
    }
    return true
}
