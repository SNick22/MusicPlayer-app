package com.azat.musicplayer.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import com.azat.auth.AuthEntryPoint
import com.azat.home.presentation.HomeScreen
import com.azat.navigation.NavigationIntent
import com.azat.navigation.VoyagerNavigationContainer
import com.azat.player.presentation.PlayerScreen
import kotlinx.coroutines.flow.SharedFlow

class RootNavigationContainer : VoyagerNavigationContainer() {

    override val startScreen: Screen = HomeScreen()

    @Composable
    override fun getNavigationEffect(): SharedFlow<NavigationIntent> {
        val effectHolder: RootNavigationEffectHolder = getViewModel()
        return effectHolder.navigationFlow
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        BottomSheetNavigator { bottomSheetNavigator ->
            Navigator(
                screen = startScreen
            ) { navigator ->
                val navigationEffect = getNavigationEffect()

                LaunchedEffect(navigationEffect) {
                    navigationEffect.collect { intent ->
                        when (intent) {
                            NavigationIntent.NavigateBack -> navigator.pop()
                            is NavigationIntent.NavigateTo -> {
                                when (intent.screen as Feature) {
                                    Feature.AuthFeature -> navigator.replaceAll(AuthEntryPoint())
                                    Feature.HomeFeature -> navigator.replace(HomeScreen())
                                    Feature.PlayerFeature -> bottomSheetNavigator.show(PlayerScreen())
                                }
                            }
                        }
                    }
                }

                NavigationWrapper(navigator = navigator) {
                    navigator.lastItem.Content()
                }
            }
        }
    }

    override fun navigate(navigator: Navigator, intent: NavigationIntent) {
        TODO("Not yet implemented")
    }
}
