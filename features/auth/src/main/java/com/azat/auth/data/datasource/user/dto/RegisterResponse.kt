package com.azat.auth.data.datasource.user.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class RegisterResponse(
    val smsId: Int
)
