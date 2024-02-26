package com.azat.widgets.text_field

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.azat.designsystem.MusicPlayerTheme

@Composable
fun MusicPlayerTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable () -> Unit = {},
    label: @Composable () -> Unit = {},
    supportingText: @Composable () -> Unit = {},
    leadingIcon: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    colors: MusicPlayerTextFieldColors = musicPlayerTextFieldColors(),
    enabled: Boolean = true,
    isError: Boolean = false,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
) {
    val contentColor = colors.contentColor(
        enabled = enabled,
        isError = isError,
        interactionSource = interactionSource
    )
    val mergedTextStyle = textStyle.merge(TextStyle(color = contentColor.value))

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
    ) { innerTextField ->

        MusicPlayerTextFieldDecorationBox(
            value = value,
            placeholder = placeholder,
            label = label,
            supportingText = supportingText,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            colors = colors,
            enabled = enabled,
            isError = isError,
            interactionSource = interactionSource,
        ) {
            innerTextField()
        }
    }
}

@Composable
private fun MusicPlayerTextFieldDecorationBox(
    value: String,
    placeholder: @Composable () -> Unit,
    label: @Composable () -> Unit,
    supportingText: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit,
    trailingIcon: @Composable () -> Unit,
    colors: MusicPlayerTextFieldColors,
    enabled: Boolean,
    isError: Boolean,
    interactionSource: MutableInteractionSource,
    content: @Composable () -> Unit
) {
    val placeholderColor = colors.placeholderColor(
        enabled = enabled,
        isError = isError,
        interactionSource = interactionSource
    )

    val labelColor = colors.labelColor(
        enabled = enabled,
        isError = isError,
        interactionSource = interactionSource
    )

    val supportingTextColor = colors.supportingTextColor(
        enabled = enabled,
        isError = isError,
        interactionSource = interactionSource
    )

    val dividerColor = colors.dividerColor(
        enabled = enabled,
        isError = isError,
        interactionSource = interactionSource
    )

    Column {
        CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.labelMedium
        ) {
            CompositionLocalProvider(LocalContentColor provides labelColor.value) {
                label()
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            leadingIcon()
            Box(modifier = Modifier.weight(1f)) {
                if (value.isEmpty()) {
                    CompositionLocalProvider(
                        LocalTextStyle provides MaterialTheme.typography.bodyMedium
                    ) {
                        CompositionLocalProvider(LocalContentColor provides placeholderColor.value) {
                            placeholder()
                        }
                    }
                }
                content()
            }
            trailingIcon()
        }

        Spacer(modifier = Modifier.height(12.dp))

        Divider(color = dividerColor.value)

        Spacer(modifier = Modifier.height(12.dp))

        CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.labelMedium
        ) {
            CompositionLocalProvider(LocalContentColor provides supportingTextColor.value) {
                supportingText()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun MusicPlayerTextFieldPreview() {
    MusicPlayerTheme {
        MusicPlayerTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = "Sample") },
            placeholder = { Text(text = "Sample") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
