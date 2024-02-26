package com.azat.musicplayer.deps_impl.auth

import com.azat.auth.AuthFeatureNavigator
import com.azat.musicplayer.navigation.Feature
import com.azat.musicplayer.navigation.RootNavigator
import javax.inject.Inject

class AuthFeatureNavigatorImpl @Inject constructor(
    private val rootNavigator: RootNavigator
): AuthFeatureNavigator {

    override suspend fun navigateToHomeFeature() {
        rootNavigator.navigateTo(screen = Feature.HomeFeature)
    }
}
