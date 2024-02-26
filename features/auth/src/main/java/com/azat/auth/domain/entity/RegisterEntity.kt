package com.azat.auth.domain.entity

internal data class RegisterEntity(
    val phoneNumber: String,
    val password: String,
    val passwordRepeat: String,
    val notificationsToken: String
)
