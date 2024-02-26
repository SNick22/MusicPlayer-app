package com.azat.widgets.text_field

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color

@Composable
@ReadOnlyComposable
fun musicPlayerTextFieldColors(
    unfocusedDividerColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    unfocusedContentColor: Color = MaterialTheme.colorScheme.onSurface,
    unfocusedLabelColor: Color = MaterialTheme.colorScheme.onSurface,
    unfocusedPlaceholderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    unfocusedSupportTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    focusedDividerColor: Color = MaterialTheme.colorScheme.primary,
    focusedContentColor: Color = MaterialTheme.colorScheme.onSurface,
    focusedLabelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    focusedPlaceholderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    focusedSupportTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    errorDividerColor: Color = MaterialTheme.colorScheme.error,
    errorContentColor: Color = MaterialTheme.colorScheme.onSurface,
    errorLabelColor: Color = MaterialTheme.colorScheme.onSurface,
    errorPlaceholderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    errorSupportTextColor: Color = MaterialTheme.colorScheme.error,
    disabledDividerColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    disabledContentColor: Color = MaterialTheme.colorScheme.onSurface,
    disabledLabelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    disabledPlaceholderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    disabledSupportTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
): MusicPlayerTextFieldColors = MusicPlayerTextFieldColors(
    unfocusedDividerColor = unfocusedDividerColor,
    unfocusedContentColor = unfocusedContentColor,
    unfocusedLabelColor = unfocusedLabelColor,
    unfocusedPlaceholderColor = unfocusedPlaceholderColor,
    unfocusedSupportTextColor = unfocusedSupportTextColor,
    focusedDividerColor = focusedDividerColor,
    focusedContentColor = focusedContentColor,
    focusedLabelColor = focusedLabelColor,
    focusedPlaceholderColor = focusedPlaceholderColor,
    focusedSupportTextColor = focusedSupportTextColor,
    errorDividerColor = errorDividerColor,
    errorContentColor = errorContentColor,
    errorLabelColor = errorLabelColor,
    errorPlaceholderColor = errorPlaceholderColor,
    errorSupportTextColor = errorSupportTextColor,
    disabledDividerColor = disabledDividerColor,
    disabledContentColor = disabledContentColor,
    disabledLabelColor = disabledLabelColor,
    disabledPlaceholderColor = disabledPlaceholderColor,
    disabledSupportTextColor = disabledSupportTextColor
)

@Immutable
data class MusicPlayerTextFieldColors internal constructor(
    val unfocusedDividerColor: Color,
    val unfocusedContentColor: Color,
    val unfocusedLabelColor: Color,
    val unfocusedPlaceholderColor: Color,
    val unfocusedSupportTextColor: Color,
    val focusedDividerColor: Color,
    val focusedContentColor: Color,
    val focusedLabelColor: Color,
    val focusedPlaceholderColor: Color,
    val focusedSupportTextColor: Color,
    val errorDividerColor: Color,
    val errorContentColor: Color,
    val errorLabelColor: Color,
    val errorPlaceholderColor: Color,
    val errorSupportTextColor: Color,
    val disabledDividerColor: Color,
    val disabledContentColor: Color,
    val disabledLabelColor: Color,
    val disabledPlaceholderColor: Color,
    val disabledSupportTextColor: Color,
) {

    @Composable
    internal fun dividerColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            isError -> errorDividerColor
            !enabled -> disabledDividerColor
            focused -> focusedDividerColor
            else -> unfocusedDividerColor
        }
        return rememberUpdatedState(targetValue)
    }

    @Composable
    internal fun contentColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            isError -> errorContentColor
            !enabled -> disabledContentColor
            focused -> focusedContentColor
            else -> unfocusedContentColor
        }
        return rememberUpdatedState(targetValue)
    }

    @Composable
    internal fun labelColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            focused -> focusedLabelColor
            isError -> errorLabelColor
            !enabled -> disabledLabelColor
            else -> unfocusedLabelColor
        }
        return rememberUpdatedState(targetValue)
    }

    @Composable
    internal fun placeholderColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            focused -> focusedPlaceholderColor
            isError -> errorPlaceholderColor
            !enabled -> disabledPlaceholderColor
            else -> unfocusedPlaceholderColor
        }
        return rememberUpdatedState(targetValue)
    }

    @Composable
    internal fun supportingTextColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            isError -> errorSupportTextColor
            focused -> focusedSupportTextColor
            !enabled -> disabledSupportTextColor
            else -> unfocusedSupportTextColor
        }
        return rememberUpdatedState(targetValue)
    }
}
