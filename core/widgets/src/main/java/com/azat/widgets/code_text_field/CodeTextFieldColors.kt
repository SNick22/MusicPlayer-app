package com.azat.widgets.code_text_field

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color

@Composable
@ReadOnlyComposable
fun codeTextFieldColors(
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    unfocusedContentColor: Color = MaterialTheme.colorScheme.onSurface,
    unfocusedPlaceholderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    focusedContentColor: Color = MaterialTheme.colorScheme.onSurface,
    focusedPlaceholderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    errorBorderColor: Color = MaterialTheme.colorScheme.error,
    errorContentColor: Color = MaterialTheme.colorScheme.error,
    errorPlaceholderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    disabledBorderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    disabledContentColor: Color = MaterialTheme.colorScheme.onSurface,
    disabledPlaceholderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
): CodeTextFieldColors = CodeTextFieldColors(
    unfocusedBorderColor = unfocusedBorderColor,
    unfocusedContentColor = unfocusedContentColor,
    unfocusedPlaceholderColor = unfocusedPlaceholderColor,
    focusedBorderColor = focusedBorderColor,
    focusedContentColor = focusedContentColor,
    focusedPlaceholderColor = focusedPlaceholderColor,
    errorBorderColor = errorBorderColor,
    errorContentColor = errorContentColor,
    errorPlaceholderColor = errorPlaceholderColor,
    disabledBorderColor = disabledBorderColor,
    disabledContentColor = disabledContentColor,
    disabledPlaceholderColor = disabledPlaceholderColor,
)

@Immutable
data class CodeTextFieldColors internal constructor(
    val unfocusedBorderColor: Color,
    val unfocusedContentColor: Color,
    val unfocusedPlaceholderColor: Color,
    val focusedBorderColor: Color,
    val focusedContentColor: Color,
    val focusedPlaceholderColor: Color,
    val errorBorderColor: Color,
    val errorContentColor: Color,
    val errorPlaceholderColor: Color,
    val disabledBorderColor: Color,
    val disabledContentColor: Color,
    val disabledPlaceholderColor: Color,
) {

    @Composable
    internal fun borderColor(
        enabled: Boolean,
        isError: Boolean,
        focused: Boolean
    ): State<Color> {

        val targetValue = when {
            isError -> errorBorderColor
            !enabled -> disabledBorderColor
            focused -> focusedBorderColor
            else -> unfocusedBorderColor
        }
        return rememberUpdatedState(targetValue)
    }

    @Composable
    internal fun contentColor(
        enabled: Boolean,
        isError: Boolean,
        focused: Boolean
    ): State<Color> {

        val targetValue = when {
            isError -> errorContentColor
            !enabled -> disabledContentColor
            focused -> focusedContentColor
            else -> unfocusedContentColor
        }
        return rememberUpdatedState(targetValue)
    }

    @Composable
    internal fun placeholderColor(
        enabled: Boolean,
        isError: Boolean,
        focused: Boolean
    ): State<Color> {

        val targetValue = when {
            focused -> focusedPlaceholderColor
            isError -> errorPlaceholderColor
            !enabled -> disabledPlaceholderColor
            else -> unfocusedPlaceholderColor
        }
        return rememberUpdatedState(targetValue)
    }
}

