package com.azat.player.presentation

import android.graphics.Bitmap
import androidx.annotation.OptIn
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Target
import coil.compose.AsyncImagePainter
import com.azat.common.formatToMinutesSeconds
import com.azat.designsystem.MusicPlayerTheme
import com.azat.player.presentation.entity.Music
import com.azat.player.presentation.mvi.PlayerEvent
import com.azat.player.presentation.mvi.PlayerState
import com.azat.player.presentation.mvi.PlayerViewModel
import com.azat.presentation.SpacerHeight
import com.azat.presentation.rememberOnEvent
import com.azat.widgets.CoilImage
import com.azat.widgets.MockMusicCover
import com.azat.widgets.R
import kotlin.math.absoluteValue
import kotlin.math.roundToLong
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun PlayerComposeScreen(
    viewModel: PlayerViewModel
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    PlayerContent(
        screenState = screenState,
        onEvent = viewModel.rememberOnEvent(),
        videoPlayer = viewModel.videoPlayer
    )
}

@Composable
private fun PlayerContent(
    screenState: PlayerState,
    onEvent: (PlayerEvent) -> Unit,
    videoPlayer: Player?
) {
    val showVideo = screenState.isVideoReady && screenState.playingMusic?.videoUrl != null
    val defaultBackgroud = MaterialTheme.colorScheme.onSurfaceVariant
    var backgroundColor by remember {
        mutableStateOf(defaultBackgroud)
    }

    val backgroundColorTransition by animateColorAsState(
        targetValue = backgroundColor,
        animationSpec = tween(100, easing = LinearEasing),
        label = "background color animation"
    )

    val backgroundAlpha by animateFloatAsState(
        targetValue = if (showVideo) 0f else 1f,
        animationSpec = tween(200, easing = LinearEasing),
        label = "background alpha"
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isSliderDragged by interactionSource.collectIsDraggedAsState()
    var sliderStateRefreshed by remember {
        mutableStateOf(false)
    }

    var sliderChangePosition by remember {
        mutableFloatStateOf(screenState.playProgress)
    }

    val coverSize by animateFloatAsState(
        targetValue = if (screenState.isPaused) .8f else 1f,
        label = "cover size",
        animationSpec = tween(150, easing = FastOutSlowInEasing)
    )

    LaunchedEffect(screenState.playProgress) {
        if ((screenState.playProgress - sliderChangePosition).absoluteValue < .01f) {
            sliderStateRefreshed = true
        }
    }

    LaunchedEffect(isSliderDragged) {
        if (isSliderDragged) {
            sliderStateRefreshed = false
        }
    }

    Box {
        if (screenState.playingMusic?.videoUrl != null) {
            MusicVideoPlayer(
                player = videoPlayer!!,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    drawRect(backgroundColorTransition.copy(alpha = backgroundAlpha))
                }
                .systemBarsPadding()
                .padding(
                    horizontal = dimensionResource(
                        id = com.azat.designsystem.R.dimen.medium_container_padding
                    )
                )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                val coverModifier = Modifier
                    .fillMaxSize(coverSize)
                    .clip(MaterialTheme.shapes.medium)
                    .graphicsLayer { alpha = backgroundAlpha }
                if (screenState.playingMusic?.largeCoverUrl != null) {
                    CoilImage(
                        photoUrl = screenState.playingMusic.largeCoverUrl,
                        modifier = coverModifier,
                        shape = MaterialTheme.shapes.medium,
                        onState = { it.getBackgroundColor()?.also { color -> backgroundColor = color } }
                    )
                } else {
                    MockMusicCover(
                        modifier = coverModifier
                    )
                }
            }

            SpacerHeight(height = 32.dp)

            val surfaceColor = MaterialTheme.colorScheme.surface

            MusicTimeSlider(
                value = if (isSliderDragged || !sliderStateRefreshed) sliderChangePosition
                    else screenState.playProgress
                ,
                onValueChange = { sliderChangePosition = it },
                onValueChangeFinished = { onEvent(PlayerEvent.OnMoveSlider(sliderChangePosition)) },
                modifier = Modifier.fillMaxWidth(),
                interactionSource = interactionSource,
                musicDuration = screenState.playingMusic?.duration?.inWholeSeconds
            )

            SpacerHeight(height = 32.dp)

            Text(
                text = screenState.playingMusic?.title ?: "",
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = screenState.playingMusic?.author ?: "",
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.bodyLarge
            )

            SpacerHeight(height = 48.dp)

            IconButton(
                onClick = {
                    onEvent(if (screenState.isPaused) PlayerEvent.OnPlay else PlayerEvent.OnPause)
                },
                modifier = Modifier
                    .size(56.dp)
                    .graphicsLayer {
                        compositingStrategy = CompositingStrategy.Offscreen
                    }
                    .drawWithCache {
                        onDrawWithContent {
                            drawContent()
                            drawCircle(
                                surfaceColor,
                                blendMode = BlendMode.Xor
                            )
                        }
                    }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = if (screenState.isPaused) R.drawable.play_icon else R.drawable.stop_icon
                    ),
                    contentDescription = "play",
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        }
    }
}

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MusicTimeSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
    onValueChangeFinished: (() -> Unit),
    musicDuration: Long?
) {
    val surfaceColor = MaterialTheme.colorScheme.surface

    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            val textStyle = MaterialTheme.typography.bodySmall
            if (musicDuration != null) {
                Text(
                    text = calculateStringMusicTime(
                        seconds = musicDuration,
                        multiplier = value
                    ),
                    style = textStyle,
                    color = MaterialTheme.colorScheme.surface,
                )

                Text(
                    text = "-" + calculateStringMusicTime(
                        seconds = musicDuration,
                        multiplier = 1f - value
                    ),
                    style = textStyle,
                    color = MaterialTheme.colorScheme.surface,
                )
            }
        }

        Slider(
            value = value,
            onValueChange = onValueChange,
            onValueChangeFinished = onValueChangeFinished,
            modifier = Modifier.fillMaxWidth(),
            interactionSource = interactionSource,
            track = { state ->
                Divider(
                    modifier = Modifier
                        .height(2.dp)
                        .background(surfaceColor.copy(alpha = .3f))
                        .drawBehind {
                            drawRect(
                                color = surfaceColor,
                                size = size.copy(width = size.width * state.value)
                            )
                        }
                )
            },
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = interactionSource,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.surface,
                        activeTrackColor = MaterialTheme.colorScheme.surface,
                        inactiveTrackColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.offset(y = 4.dp, x = 4.dp),
                    thumbSize = DpSize(10.dp, 10.dp)
                )
            }
        )
    }
}

@OptIn(UnstableApi::class)
@Composable
private fun MusicVideoPlayer(
    player: Player,
    modifier: Modifier = Modifier
) {
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AndroidView(
        factory = {
            PlayerView(it).apply {
                this.player = player
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                useController = false
            }
        },
        update = {
            when (lifecycle) {
                Lifecycle.Event.ON_PAUSE -> {
                    it.onPause()
                    it.player?.pause()
                }
                Lifecycle.Event.ON_RESUME -> {
                    it.onResume()
                }
                else -> Unit
            }
        },
        modifier = modifier
    )
}

@Composable
@Preview(showBackground = true)
private fun PlayerPreview() {
    MusicPlayerTheme {
        PlayerContent(
            screenState = PlayerState(
                playProgress = .5f,
                playingMusic = Music(
                    id = 0,
                    title = "Sample",
                    version = "prod. Sample",
                    author = "Saple",
                    audioUrl = "",
                    videoUrl = null,
                    smallCoverUrl = null,
                    largeCoverUrl = null,
                    duration = (200L).seconds
                ),
                isPaused = true,
                isVideoReady = false,
            ),
            onEvent = {},
            videoPlayer = null
        )
    }
}

private fun AsyncImagePainter.State.getBackgroundColor(): Color? {
    return if (this is AsyncImagePainter.State.Success) {
        val bitmap = result.drawable.toBitmap().copy(Bitmap.Config.RGBA_F16, false)
        val palette = Palette.from(bitmap).addTarget(Target.LIGHT_VIBRANT).generate().run {
            mutedSwatch ?: dominantSwatch
        }
        return palette?.rgb?.let { Color(it) }
    } else {
        null
    }
}

private fun calculateStringMusicTime(seconds: Long, multiplier: Float): String {
    val duration = (seconds * multiplier).roundToLong().seconds
    return duration.formatToMinutesSeconds()
}
