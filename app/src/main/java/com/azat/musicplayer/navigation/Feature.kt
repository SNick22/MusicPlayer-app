package com.azat.musicplayer.navigation

import com.azat.navigation.Destination

sealed interface Feature: Destination {
    data object AuthFeature: Feature
    data object HomeFeature: Feature
    data object PlayerFeature: Feature
}
