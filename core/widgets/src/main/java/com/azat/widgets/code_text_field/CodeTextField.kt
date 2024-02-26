package com.azat.widgets.code_text_field

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.azat.designsystem.MusicPlayerTheme

@Composable
fun CodeTextField(
    codeLength: Int,
    value: String,
    onValueChange: (String) -> Unit,
    onEnterComplete: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    colors: CodeTextFieldColors = codeTextFieldColors(),
    enabled: Boolean = true,
    isError: Boolean = false,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    BasicTextField(
        value = value,
        onValueChange = {
            if (it.length <= codeLength) {
                onValueChange(it)
                if (it.length == codeLength) {
                    onEnterComplete()
                }
            }
        },
        modifier = modifier,
        singleLine = true,
        maxLines = 1,
        enabled = enabled,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
    ) {

        CodeTextFieldDecorationBox(
            codeLength =codeLength ,
            value = value,
            placeholder = placeholder,
            colors = colors,
            enabled = enabled,
            isError = isError,
            textStyle = textStyle,
            interactionSource = interactionSource
        )
    }
}

@Composable
private fun CodeTextFieldDecorationBox(
    codeLength: Int,
    value: String,
    placeholder: String,
    colors: CodeTextFieldColors,
    enabled: Boolean,
    isError: Boolean,
    textStyle: TextStyle,
    interactionSource: MutableInteractionSource,
) {
    val focused by interactionSource.collectIsFocusedAsState()

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(codeLength) { index ->
            val isSectionFocused = focused && index == value.length
            val sectionValue = value.getOrNull(index)
            val sectionPlaceholder = placeholder.getOrNull(index)
            val sectionTextColor = if (sectionValue != null) {
                colors.contentColor(
                    enabled = enabled,
                    isError = isError,
                    focused = isSectionFocused
                )
            } else {
                colors.placeholderColor(
                    enabled = enabled,
                    isError = isError,
                    focused = isSectionFocused
                )
            }
            val sectionTextStyle = textStyle.merge(TextStyle(color = sectionTextColor.value))
            val borderColor = colors.borderColor(
                enabled = enabled,
                isError = isError,
                focused = isSectionFocused
            )

            Text(
                text = sectionValue?.toString() ?: sectionPlaceholder?.toString() ?: "",
                style = sectionTextStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(64.dp)
                    .border(
                        width = 1.dp,
                        color = borderColor.value,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(16.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CodeTextFieldPreview() {
    MusicPlayerTheme {
        CodeTextField(
            value = "",
            onValueChange = {},
            onEnterComplete = {},
            placeholder = "0000",
            codeLength = 4,
        )
    }
}
