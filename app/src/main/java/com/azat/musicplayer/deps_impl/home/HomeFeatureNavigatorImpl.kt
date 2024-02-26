package com.azat.musicplayer.deps_impl.home

import com.azat.home.HomeFeatureNavigator
import com.azat.musicplayer.navigation.Feature
import com.azat.musicplayer.navigation.RootNavigator
import javax.inject.Inject

class HomeFeatureNavigatorImpl @Inject constructor(
    private val rootNavigator: RootNavigator
): HomeFeatureNavigator {

    override suspend fun openPlayer() {
        rootNavigator.navigateTo(Feature.PlayerFeature)
    }
}
