package com.azat.player.di

import android.content.ComponentName
import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.LoadControl
import androidx.media3.exoplayer.upstream.Allocator
import androidx.media3.exoplayer.upstream.DefaultAllocator
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.azat.player.presentation.service.PlayerService
import com.google.common.util.concurrent.ListenableFuture
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class PlayerModule {

    @Provides
    fun provideSessionToken(@ApplicationContext appContext: Context): SessionToken {
        return SessionToken(appContext, ComponentName(appContext, PlayerService::class.java))
    }

    @Provides
    fun provideMediaControllerFuture(
        @ApplicationContext appContext: Context,
        sessionToken: SessionToken
    ): ListenableFuture<MediaController> {
        return MediaController.Builder(appContext, sessionToken)
            .buildAsync()
    }

    @Provides
    fun provideVideoPlayer(@ApplicationContext appContext: Context): Player {
        return ExoPlayer.Builder(appContext).build()
    }
}
