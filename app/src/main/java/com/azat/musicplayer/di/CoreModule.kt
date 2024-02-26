package com.azat.musicplayer.di

import android.content.Context
import com.azat.common.CoreProvider
import com.azat.common_impl.CoreProviderImpl
import com.azat.musicplayer.navigation.VoyagerAppRestarter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    fun provideCoreProvider(
        @ApplicationContext appContext: Context,
        voyagerAppRestarter: VoyagerAppRestarter
    ): CoreProvider {
        return CoreProviderImpl(
            appContext = appContext,
            appRestarter = voyagerAppRestarter
        )
    }
}
