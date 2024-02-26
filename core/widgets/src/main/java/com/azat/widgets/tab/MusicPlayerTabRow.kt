package com.azat.widgets.tab

import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.azat.designsystem.MusicPlayerTheme

@Composable
fun MusicPlayerTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Transparent,
    tabs: @Composable () -> Unit
) {
    TabRow(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        containerColor = containerColor,
        tabs = tabs
    )
}

@Composable
@Preview(showBackground = true)
private fun MusicPlayerTabRowPreview() {
    MusicPlayerTheme {
        MusicPlayerTabRow(
            selectedTabIndex = 0
        ) {
            MusicPlayerTab(
                selected = true,
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Sample")
            }

            MusicPlayerTab(
                selected = false,
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Sample")
            }
        }
    }
}
