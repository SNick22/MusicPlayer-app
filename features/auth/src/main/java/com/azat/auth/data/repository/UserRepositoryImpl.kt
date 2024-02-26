package com.azat.auth.data.repository

import com.azat.auth.data.datasource.user.UserDatasource
import com.azat.auth.data.datasource.user.dto.LoginRequest
import com.azat.auth.data.datasource.user.dto.RegisterConfirmRequest
import com.azat.auth.data.datasource.user.dto.RegisterRequest
import com.azat.auth.data.datasource.user.dto.RegisterResendCodeRequest
import com.azat.auth.data.mappers.toRegisterRequest
import com.azat.auth.domain.entity.RegisterEntity
import com.azat.auth.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val userDatasource: UserDatasource
): UserRepository {

    override suspend fun login(phoneNumber: String, password: String): Result<String> {
        val request = LoginRequest(phoneNumber = phoneNumber, password = password)
        return userDatasource.login(request).mapCatching { it.token }
    }

    override suspend fun register(registerEntity: RegisterEntity): Result<Int> {
        val request = registerEntity.toRegisterRequest()
        return userDatasource.register(request).mapCatching { it.smsId }
    }

    override suspend fun confirmRegister(smsId: Int, code: String): Result<Unit> {
        val confirmRegisterRequest = RegisterConfirmRequest(smsId = smsId, code = code)
        return userDatasource.confirmRegister(confirmRegisterRequest)
    }

    override suspend fun resendCode(smsId: Int): Result<Unit> {
        val resendCodeRequest = RegisterResendCodeRequest(smsId = smsId)
        return userDatasource.resendCode(resendCodeRequest)
    }
}
