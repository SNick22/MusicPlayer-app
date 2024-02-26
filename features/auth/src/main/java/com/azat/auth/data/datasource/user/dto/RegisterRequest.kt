package com.azat.auth.data.datasource.user.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class RegisterRequest(
    val phoneNumber: String,
    val password: String,
    val notificationsToken: String
)
