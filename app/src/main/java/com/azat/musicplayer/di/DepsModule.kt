package com.azat.musicplayer.di

import com.azat.auth.AuthFeatureNavigator
import com.azat.home.HomeFeatureNavigator
import com.azat.home.PlayerConnection
import com.azat.musicplayer.deps_impl.auth.AuthFeatureNavigatorImpl
import com.azat.musicplayer.deps_impl.home.HomeFeatureNavigatorImpl
import com.azat.musicplayer.deps_impl.home.PlayerControllerAdapter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DepsModule {

    @Binds
    fun bindAuthFeatureNavigator(impl: AuthFeatureNavigatorImpl): AuthFeatureNavigator

    @Binds
    fun bindPlayerConnection(impl: PlayerControllerAdapter): PlayerConnection

    @Binds
    fun bindHomeFeatureNavigator(impl: HomeFeatureNavigatorImpl): HomeFeatureNavigator
}
