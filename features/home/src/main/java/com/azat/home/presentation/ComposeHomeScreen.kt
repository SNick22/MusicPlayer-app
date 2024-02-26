package com.azat.home.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.azat.common.formatToMinutesSeconds
import com.azat.designsystem.MusicPlayerTheme
import com.azat.home.R
import com.azat.home.presentation.entity.Music
import com.azat.home.presentation.mvi.HomeEvent
import com.azat.home.presentation.mvi.HomeState
import com.azat.home.presentation.mvi.HomeViewModel
import com.azat.presentation.noRippleClickable
import com.azat.presentation.rememberOnEvent
import com.azat.presentation.shimmerEffect
import com.azat.widgets.CoilImage
import com.azat.widgets.MockMusicCover
import com.azat.widgets.MusicItem
import com.azat.widgets.MusicPlayerAppBar
import com.azat.widgets.NowPlayingBottomBar

@Composable
internal fun ComposeHomeScreen(
    viewModel: HomeViewModel
) {
    val musicList = viewModel.musicPagingFlow.collectAsLazyPagingItems()
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    HomeContent(
        musicList = musicList,
        screenState = screenState,
        onEvent = viewModel.rememberOnEvent()
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun HomeContent(
    musicList: LazyPagingItems<Music>?,
    screenState: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    Scaffold(
        topBar = {
            HomeAppBar(
                onEvent = onEvent
            )
        },
        bottomBar = {
            if (screenState.nowPlayingMusic != null) {
                NowPlayingBottomBar(
                    title = { Text(text = screenState.nowPlayingMusic.title) },
                    singer = { Text(text = screenState.nowPlayingMusic.author) },
                    version = { screenState.nowPlayingMusic.version?.let { Text(text = it) } },
                    cover = {
                        val modifier = Modifier
                            .size(dimensionResource(id = com.azat.designsystem.R.dimen.list_cover_size))
                            .clip(MaterialTheme.shapes.extraSmall)
                        if (screenState.nowPlayingMusic.smallCoverUrl != null) {
                            CoilImage(
                                photoUrl = screenState.nowPlayingMusic.smallCoverUrl,
                                modifier = modifier
                            )
                        } else {
                            MockMusicCover(
                                modifier = modifier
                            )
                        }
                    },
                    isPlaying = screenState.isPlaying,
                    onPlayingChange = { onEvent(HomeEvent.OnChangeIsPlaying(it)) },
                    progressProvider = { screenState.playProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable { onEvent(HomeEvent.OnOpenPlayer) }
                )
            }
        }
    ) { paddingValues ->

        if (musicList != null) {
            MainContent(
                musicList = musicList,
                modifier = Modifier
                    .padding(paddingValues)
                ,
                screenState = screenState,
                onEvent = onEvent
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainContent(
    musicList: LazyPagingItems<Music>,
    screenState: HomeState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val isInitialLoading =
        musicList.loadState.refresh == LoadState.Loading && musicList.itemCount == 0
    val isRefreshing =
        musicList.loadState.refresh == LoadState.Loading && musicList.itemCount != 0

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { musicList.refresh() }
    )

    Box(
        modifier = modifier
    ) {
        LazyColumn(
            userScrollEnabled = !isInitialLoading,
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .fillMaxSize()
        ) {
            items(
                count = musicList.itemCount,
            ) { index ->
                val music = musicList[index]
                if (music != null) {
                    key(music.id) {
                        HomeMusicItem(
                            title = music.title,
                            version = music.version,
                            singer = music.author,
                            coverUrl = music.smallCoverUrl,
                            duration = music.duration.formatToMinutesSeconds(),
                            isPlaying = screenState.nowPlayingMusic?.id == music.id,
                            onClick = { onEvent(HomeEvent.OnMusicClick(music)) }
                        )
                    }
                }
            }

            if (isInitialLoading) {
                val titleModifier = Modifier
                    .height(16.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
                val singerModifier = Modifier
                    .height(16.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
                items(20) {
                    MusicItem(
                        title = {
                            Spacer(
                                modifier = titleModifier
                            )
                        },
                        singer = {
                            Spacer(
                                modifier = singerModifier
                            )
                        },
                        cover = {
                            Spacer(
                                modifier = Modifier
                                    .size(dimensionResource(id = com.azat.designsystem.R.dimen.list_cover_size))
                                    .clip(MaterialTheme.shapes.extraSmall)
                                    .shimmerEffect()
                            )
                        },
                    )
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
internal fun HomeMusicItem(
    title: String,
    version: String?,
    singer: String,
    coverUrl: String?,
    duration: String,
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    MusicItem(
        title = {
            Text(
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        version = {
            version?.let {
                Text(
                    text = it,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        },
        singer = {
            Text(
                text = singer,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        duration = { Text(text = duration) },
        onClick = onClick,
        isPlayingNow = isPlaying,
        cover = {
            val modifier = Modifier
                .size(dimensionResource(id = com.azat.designsystem.R.dimen.list_cover_size))
                .clip(MaterialTheme.shapes.extraSmall)
            if (coverUrl != null) {
                CoilImage(
                    photoUrl = coverUrl,
                    modifier = modifier
                )
            } else {
                MockMusicCover(
                    modifier = modifier
                )
            }
        },
        trailingIcons = {
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = com.azat.widgets.R.drawable.meatballs_menu_icon
                    ),
                    contentDescription = "menu"
                )
            }
        }
    )
}

@Composable
private fun HomeAppBar(
    onEvent: (HomeEvent) -> Unit
) {
    MusicPlayerAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_bar_label)
            )
        },
        actions = {
            IconButton(
                onClick = { onEvent(HomeEvent.OnNavigateToSearch) }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.search_icon),
                    contentDescription = "search"
                )
            }
        },
    )
}

@Composable
@Preview(showBackground = true)
private fun HomePreview() {
    MusicPlayerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeContent(
                screenState = HomeState(),
                onEvent = {},
                musicList = null
            )
        }
    }
}
