package com.azat.auth.domain.exceptions

import com.azat.common.exceptions.AppException
import com.azat.common_impl.UiText

internal class InvalidPasswordException(val errorMessage: UiText): AppException()
