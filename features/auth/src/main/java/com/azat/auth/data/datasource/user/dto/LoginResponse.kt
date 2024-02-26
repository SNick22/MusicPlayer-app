package com.azat.auth.data.datasource.user.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginResponse(
    val token: String
)
