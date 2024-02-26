package com.azat.auth.presentation

import com.azat.navigation.NavigationEffectHolder
import com.azat.navigation.NavigationIntent
import com.azat.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
internal class AuthNavigationEffectHolder @Inject constructor(
    private val authNavigator: AuthNavigator
): BaseViewModel(), NavigationEffectHolder {

    override val navigationFlow: SharedFlow<NavigationIntent>
        get() = authNavigator.navigationFlow
}
