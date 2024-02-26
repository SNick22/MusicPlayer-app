package com.azat.auth.data.datasource.user

import com.azat.auth.data.datasource.user.dto.LoginRequest
import com.azat.auth.data.datasource.user.dto.LoginResponse
import com.azat.auth.data.datasource.user.dto.RegisterConfirmRequest
import com.azat.auth.data.datasource.user.dto.RegisterRequest
import com.azat.auth.data.datasource.user.dto.RegisterResendCodeRequest
import com.azat.auth.data.datasource.user.dto.RegisterResponse

internal interface UserDatasource {

    suspend fun register(request: RegisterRequest): Result<RegisterResponse>

    suspend fun confirmRegister(request: RegisterConfirmRequest): Result<Unit>

    suspend fun resendCode(request: RegisterResendCodeRequest): Result<Unit>

    suspend fun login(request: LoginRequest): Result<LoginResponse>
}
