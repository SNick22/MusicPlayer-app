package com.azat.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.azat.designsystem.MusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    addition: @Composable ColumnScope.() -> Unit = {}
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val appBarColor = MaterialTheme.colorScheme.surface
    val onSecondaryContainer = MaterialTheme.colorScheme.onSecondaryContainer

    Column(
        modifier = modifier
            .background(color = appBarColor)
    ) {
        TopAppBar(
            title = {
                CompositionLocalProvider(LocalContentColor provides primaryColor) {
                    title()
                }
            },
            navigationIcon = {
                CompositionLocalProvider(LocalContentColor provides primaryColor) {
                    navigationIcon()
                }
            },
            actions = {
                CompositionLocalProvider(LocalContentColor provides onSecondaryContainer) {
                    actions()
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )

        addition()
    }
}

@Composable
@Preview
private fun MusicPlayerAppBarPreview() {
    MusicPlayerTheme {
        MusicPlayerAppBar(
            title = { Text(text = "Sample") },
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                }
            }
        )
    }
}
