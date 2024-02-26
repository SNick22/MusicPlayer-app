package com.azat.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.azat.designsystem.MusicPlayerTheme

@Composable
fun NowPlayingBottomBar(
    title: @Composable () -> Unit,
    version: @Composable () -> Unit,
    singer: @Composable () -> Unit,
    cover: @Composable () -> Unit,
    isPlaying: Boolean,
    onPlayingChange: (Boolean) -> Unit,
    progressProvider: () -> Float,
    modifier: Modifier = Modifier
) {
    val titleStyle = MaterialTheme.typography.bodyMedium
    val singerStyle = MaterialTheme.typography.bodySmall
    val primaryColor = MaterialTheme.colorScheme.primary
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariantColor = MaterialTheme.colorScheme.onSurfaceVariant
    val outlineVariantColor = MaterialTheme.colorScheme.outlineVariant

    val density = LocalDensity.current
    val lineHeight = density.density * 2

    HorizontalDivider()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
            .drawBehind {
                drawLine(
                    color = outlineVariantColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = lineHeight
                )
                drawLine(
                    color = primaryColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width * progressProvider(), 0f),
                    strokeWidth = lineHeight
                )
            }
            .padding(dimensionResource(id = com.azat.designsystem.R.dimen.small_container_padding))
    ) {
        Box(modifier = Modifier.weight(1f)) {
            cover()
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                CompositionLocalProvider(LocalTextStyle provides titleStyle) {
                    CompositionLocalProvider(LocalContentColor provides onSurfaceColor) {
                        title()
                    }
                }

                Spacer(modifier = Modifier.width(4.dp))

                CompositionLocalProvider(LocalTextStyle provides titleStyle) {
                    CompositionLocalProvider(LocalContentColor provides onSurfaceVariantColor) {
                        version()
                    }
                }
            }

            CompositionLocalProvider(LocalTextStyle provides singerStyle) {
                CompositionLocalProvider(LocalContentColor provides onSurfaceVariantColor) {
                    singer()
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(
                onClick = { onPlayingChange(!isPlaying) }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = if (isPlaying) R.drawable.stop_icon else R.drawable.play_icon
                    ),
                    contentDescription = "play",
                    tint = primaryColor
                )
            }
        }
    }
}

@Composable
@Preview
private fun NowPlayingBottomBarPreview() {
    MusicPlayerTheme {
        NowPlayingBottomBar(
            title = { Text(text = "Sample") },
            singer = { Text(text = "Sample") },
            version = { Text(text = "feat Sample") },
            cover = {
                MockMusicCover(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            },
            isPlaying = true,
            onPlayingChange = {},
            progressProvider = { .5f },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
