package com.azat.navigation

import kotlinx.coroutines.flow.SharedFlow

interface NavigationEffectHolder {

    val navigationFlow: SharedFlow<NavigationIntent>
}
