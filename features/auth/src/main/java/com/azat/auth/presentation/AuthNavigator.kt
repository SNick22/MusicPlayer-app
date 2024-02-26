package com.azat.auth.presentation

import com.azat.navigation.BaseScreenNavigator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AuthNavigator @Inject constructor(): BaseScreenNavigator<AuthScreens>()
