package com.azat.navigation

sealed interface NavigationIntent {

    data class NavigateTo(val screen: Destination): NavigationIntent

    data object NavigateBack: NavigationIntent
}
