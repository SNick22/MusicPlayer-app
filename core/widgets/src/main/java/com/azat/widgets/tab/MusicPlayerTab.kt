package com.azat.widgets.tab

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.azat.designsystem.MusicPlayerTheme

@Composable
fun MusicPlayerTab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    val textColor = if (selected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurfaceVariant
    val textStyle = MaterialTheme.typography.bodyMedium
    
    Column(
        modifier = modifier
            .selectable(
                selected = selected,
                enabled = enabled,
                indication = null,
                interactionSource = interactionSource,
                onClick = onClick
            )
            .padding(
                top = 4.dp,
                bottom = 16.dp
            )
            .fillMaxWidth()
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            CompositionLocalProvider(LocalTextStyle provides textStyle) {
                CompositionLocalProvider(LocalContentColor provides textColor) {
                    content()
                }
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun MusicPlayerTabPreview() {
    MusicPlayerTheme {
        MusicPlayerTab(
            selected = true,
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Sample")
        }
    }
}
