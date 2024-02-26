package com.azat.common_impl

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {

    data class DynamicString(val value: String): UiText()
    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = emptyArray()
    ): UiText()
}

@Composable
fun UiText.asString(): String =
    when(this) {
        is UiText.DynamicString -> this.value
        is UiText.StringResource -> stringResource(id = this.id, args)
    }

fun UiText.asString(resources: Resources): String =
    when(this) {
        is UiText.DynamicString -> this.value
        is UiText.StringResource -> resources.getString(this.id, args)
    }
