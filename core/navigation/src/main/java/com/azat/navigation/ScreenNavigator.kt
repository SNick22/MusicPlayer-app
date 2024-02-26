package com.azat.navigation

interface ScreenNavigator<D: Destination>: NavigationEffectHolder {

    suspend fun navigateTo(screen: D)

    suspend fun navigateBack()
}
