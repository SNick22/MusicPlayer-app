package com.azat.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

open class BaseScreenNavigator<D: Destination> : ScreenNavigator<D> {

    private val _navigationFlow: MutableSharedFlow<NavigationIntent> = MutableSharedFlow()
    override val navigationFlow: SharedFlow<NavigationIntent>
        get() = _navigationFlow.asSharedFlow()

    override suspend fun navigateTo(screen: D) {
        _navigationFlow.emit(
            NavigationIntent.NavigateTo(screen = screen)
        )
    }

    override suspend fun navigateBack() {
        _navigationFlow.emit(
            NavigationIntent.NavigateBack
        )
    }
}