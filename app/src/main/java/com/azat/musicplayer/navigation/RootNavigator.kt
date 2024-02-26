package com.azat.musicplayer.navigation

import com.azat.navigation.BaseScreenNavigator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RootNavigator @Inject constructor(): BaseScreenNavigator<Feature>()
