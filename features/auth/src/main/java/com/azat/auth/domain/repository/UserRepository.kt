package com.azat.auth.domain.repository

import com.azat.auth.domain.entity.RegisterEntity

internal interface UserRepository {

    suspend fun login(phoneNumber: String, password: String): Result<String>

    suspend fun register(registerEntity: RegisterEntity): Result<Int>

    suspend fun confirmRegister(smsId: Int, code: String): Result<Unit>

    suspend fun resendCode(smsId: Int): Result<Unit>
}
