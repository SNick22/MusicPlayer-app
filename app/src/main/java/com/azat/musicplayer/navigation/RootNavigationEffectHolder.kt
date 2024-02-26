package com.azat.musicplayer.navigation

import com.azat.navigation.NavigationEffectHolder
import com.azat.navigation.NavigationIntent
import com.azat.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class RootNavigationEffectHolder @Inject constructor(
    private val rootNavigator: RootNavigator,
): BaseViewModel(), NavigationEffectHolder {

    override val navigationFlow: SharedFlow<NavigationIntent>
        get() = rootNavigator.navigationFlow
}
