package com.azat.widgets

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.azat.designsystem.MusicPlayerTheme

@Composable
fun MusicItem(
    title: @Composable () -> Unit,
    version: @Composable () -> Unit = {},
    singer: @Composable () -> Unit,
    cover: @Composable () -> Unit,
    duration: @Composable () -> Unit = {},
    isPlayingNow: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    mutableInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    leadingIcon: @Composable () -> Unit = {},
    trailingIcons: @Composable RowScope.() -> Unit = {},
) {
    val indication = LocalIndication.current

    val titleStyle = MaterialTheme.typography.bodyMedium
    val singerStyle = MaterialTheme.typography.bodyMedium
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariantColor = MaterialTheme.colorScheme.onSurfaceVariant
    val localRipple = LocalRippleTheme.current
    val playingColor = localRipple.defaultColor()
        .copy(alpha = localRipple.rippleAlpha().pressedAlpha)

    Row(
        modifier
            .then(
                if (isPlayingNow) Modifier.background(playingColor)
                else Modifier
            )
            .then(
                if (onClick != null) {
                    Modifier.clickable(
                        interactionSource = mutableInteractionSource,
                        indication = indication,
                        onClick = onClick
                    )
                } else {
                    Modifier
                }
            )
            .padding(
                top = dimensionResource(
                    id = com.azat.designsystem.R.dimen.small_container_padding
                ),
                bottom = dimensionResource(
                    id = com.azat.designsystem.R.dimen.small_container_padding
                ),
                start = dimensionResource(
                    id = com.azat.designsystem.R.dimen.medium_container_padding
                )
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon()

        cover()

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
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
            
            Spacer(modifier = Modifier.height(4.dp))

            CompositionLocalProvider(LocalTextStyle provides singerStyle) {
                CompositionLocalProvider(LocalContentColor provides onSurfaceVariantColor) {
                    singer()
                }
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        CompositionLocalProvider(LocalTextStyle provides singerStyle) {
            CompositionLocalProvider(LocalContentColor provides onSurfaceVariantColor) {
                duration()
            }
        }

        CompositionLocalProvider(LocalContentColor provides onSurfaceVariantColor) {
            trailingIcons()
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun MusicItemPreview() {
    MusicPlayerTheme {
        MusicItem(
            title = { Text(text = "Sample") },
            version = { Text(text = "sample") },
            singer = { Text(text = "Sample") },
            cover = {
                MockMusicCover(
                    modifier = Modifier
                        .size(dimensionResource(id = com.azat.designsystem.R.dimen.list_cover_size))
                        .clip(MaterialTheme.shapes.extraSmall)
                )
            },
            duration = { Text(text = "2:34") },
            leadingIcon = {},
            onClick = {},
            trailingIcons = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.meatballs_menu_icon),
                        contentDescription = "menu",
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isPlayingNow = true
        )
    }
}
