package com.azat.auth.data.mappers

import com.azat.auth.data.datasource.user.dto.RegisterRequest
import com.azat.auth.domain.entity.RegisterEntity

internal fun RegisterEntity.toRegisterRequest(): RegisterRequest =
    RegisterRequest(
        phoneNumber = this.phoneNumber,
        password = this.password,
        notificationsToken = this.notificationsToken
    )
