package com.azat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import kotlinx.coroutines.flow.SharedFlow

abstract class VoyagerNavigationContainer: Screen {

    protected abstract val startScreen: Screen

    @Composable
    protected abstract fun getNavigationEffect(): SharedFlow<NavigationIntent>

    protected abstract fun navigate(navigator: Navigator, intent: NavigationIntent)

    @Composable
    override fun Content() {
        Navigator(
            screen = startScreen
        ) { navigator ->
            val navigationEffect = getNavigationEffect()

            LaunchedEffect(navigationEffect) {
                navigationEffect.collect { intent ->
                    navigate(
                        navigator = navigator,
                        intent = intent
                    )
                }
            }

            NavigationWrapper(navigator = navigator) {
                navigator.lastItem.Content()
            }
        }
    }

    @Composable
    protected open fun NavigationWrapper(
        navigator: Navigator,
        content: @Composable () -> Unit
    ) {
        content()
    }
}
