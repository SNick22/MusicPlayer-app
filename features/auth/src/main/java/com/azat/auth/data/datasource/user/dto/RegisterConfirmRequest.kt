package com.azat.auth.data.datasource.user.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class RegisterConfirmRequest(
    val smsId: Int,
    val code: String
)
