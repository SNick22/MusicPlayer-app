package com.azat.auth.data.datasource.user

import com.azat.auth.R
import com.azat.auth.data.datasource.user.dto.LoginRequest
import com.azat.auth.data.datasource.user.dto.LoginResponse
import com.azat.auth.data.datasource.user.dto.RegisterConfirmRequest
import com.azat.auth.data.datasource.user.dto.RegisterRequest
import com.azat.auth.data.datasource.user.dto.RegisterResendCodeRequest
import com.azat.auth.data.datasource.user.dto.RegisterResponse
import com.azat.auth.domain.exceptions.IncorrectCodeException
import com.azat.auth.domain.exceptions.IncorrectPhoneOrPasswordException
import com.azat.auth.domain.exceptions.UserExistException
import com.azat.common_impl.UiText
import com.azat.network.Paths
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

internal class KtorUserDatasource @Inject constructor(
    private val client: HttpClient
): UserDatasource {

    override suspend fun register(request: RegisterRequest): Result<RegisterResponse> =
        runCatching {
            try {
                client.post(Paths.REGISTER) {
                    setBody(request)
                }.body<RegisterResponse>()
            } catch (e: ClientRequestException) {
                throw UserExistException(UiText.StringResource(R.string.user_already_exist))
            }
        }

    override suspend fun confirmRegister(request: RegisterConfirmRequest): Result<Unit> =
        runCatching {
            try {
                client.post(Paths.REGISTER_CONFIRM) {
                    setBody(request)
                }
            } catch (e: ClientRequestException) {
                throw IncorrectCodeException()
            }
        }

    override suspend fun resendCode(request: RegisterResendCodeRequest): Result<Unit> =
        runCatching {
            client.post(Paths.REGISTER_RESEND_CODE) {
                setBody(request)
            }
        }

    override suspend fun login(request: LoginRequest): Result<LoginResponse> =
        runCatching {
            try {
                client.post(Paths.LOGIN) {
                    setBody(request)
                }.body<LoginResponse>()
            } catch (e: ClientRequestException) {
                throw IncorrectPhoneOrPasswordException(
                    UiText.StringResource(R.string.incorrect_phone_or_password)
                )
            }
        }
}
