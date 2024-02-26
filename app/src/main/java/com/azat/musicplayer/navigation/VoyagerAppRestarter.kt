package com.azat.musicplayer.navigation

import com.azat.common.AppRestarter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class VoyagerAppRestarter @Inject constructor(
    private val rootNavigator: RootNavigator
): AppRestarter {

    override fun restartApp() {
        CoroutineScope(Job()).launch {
            rootNavigator.navigateTo(Feature.AuthFeature)
        }
    }
}
