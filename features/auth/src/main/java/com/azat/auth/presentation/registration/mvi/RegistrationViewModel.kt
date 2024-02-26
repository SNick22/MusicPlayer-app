package com.azat.auth.presentation.registration.mvi

import com.azat.auth.domain.entity.RegisterEntity
import com.azat.auth.domain.exceptions.InvalidPasswordException
import com.azat.auth.domain.exceptions.InvalidPhoneException
import com.azat.auth.domain.exceptions.PasswordsMismatchException
import com.azat.auth.domain.exceptions.UserExistException
import com.azat.auth.domain.usecase.RegisterUseCase
import com.azat.auth.presentation.AuthNavigator
import com.azat.auth.presentation.AuthScreens
import com.azat.common_impl.UiText
import com.azat.presentation.MviViewModel
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
internal class RegistrationViewModel @Inject constructor(
    private val authNavigator: AuthNavigator,
    private val registerUseCase: RegisterUseCase
): MviViewModel<RegistrationEvent>() {

    private val _screenState = MutableStateFlow(RegistrationState())
    val screenState: StateFlow<RegistrationState>
        get() = _screenState.asStateFlow()

    private val _screenEffect = MutableSharedFlow<RegistrationEffect>()
    val screenEffect: SharedFlow<RegistrationEffect>
        get() = _screenEffect.asSharedFlow()

    private fun changeLoadingState(value: Boolean) {
        _screenState.value = screenState.value.copy(
            loading = value
        )
    }

    override fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnChangePassword -> onChangePassword(event.value)
            is RegistrationEvent.OnChangePasswordRepeat -> onChangePasswordRepeat(event.value)
            is RegistrationEvent.OnChangePhoneNumber -> onChangePhoneNumber(event.value)
            RegistrationEvent.OnRegister -> onRegister()
        }
    }

    private fun onChangePassword(value: String) {
        _screenState.value = screenState.value.copy(
            password = screenState.value.password.copy(value = value)
        )
    }

    private fun onChangePasswordRepeat(value: String) {
        _screenState.value = screenState.value.copy(
            passwordRepeat = screenState.value.passwordRepeat.copy(value = value)
        )
    }

    private fun onChangePhoneNumber(value: String) {
        _screenState.value = screenState.value.copy(
            phoneNumber = screenState.value.phoneNumber.copy(value = value)
        )
    }

    private fun onRegister() {
        changeLoadingState(true)
        viewModelScope.launch {
            val registerEntity = RegisterEntity(
                phoneNumber = screenState.value.phoneNumber.value,
                password = screenState.value.password.value,
                passwordRepeat = screenState.value.passwordRepeat.value,
                notificationsToken = fetchNotificationsToken()
            )
            registerUseCase(registerEntity).fold(
                onSuccess = { smsId ->
                    authNavigator.navigateTo(AuthScreens.RegistrationConfirm(smsId))
                },
                onFailure = { exception ->
                    clearErrors()
                    when (exception) {
                        is InvalidPhoneException -> handleInvalidPhone(exception.errorMessage)
                        is InvalidPasswordException -> handleInvalidPassword(exception.errorMessage)
                        is PasswordsMismatchException ->
                            handlePasswordMismatch(exception.errorMessage)
                        is UserExistException -> handleUserExist(exception.errorMessage)
                        else -> throw exception
                    }
                }
            )
        }.invokeOnCompletion {
            changeLoadingState(false)
        }
    }

    private fun handleUserExist(message: UiText) {
        viewModelScope.launch {
            _screenEffect.emit(RegistrationEffect.ShowToast(message))
        }
    }

    private fun handlePasswordMismatch(message: UiText) {
        _screenState.value = screenState.value.copy(
            password = screenState.value.password.copy(
                isError = true
            ),
            passwordRepeat = screenState.value.passwordRepeat.copy(
                isError = true,
                validateMessage = message
            )
        )
    }

    private fun handleInvalidPhone(message: UiText) {
        _screenState.value = screenState.value.copy(
            phoneNumber = screenState.value.phoneNumber.copy(
                isError = true,
                validateMessage = message
            )
        )
    }

    private fun handleInvalidPassword(message: UiText) {
        _screenState.value = screenState.value.copy(
            password = screenState.value.password.copy(
                isError = true
            ),
            passwordRepeat = screenState.value.passwordRepeat.copy(
                isError = true,
                validateMessage = message
            )
        )
    }

    private suspend fun fetchNotificationsToken(): String {
        return FirebaseMessaging.getInstance().token.await()
    }

    private fun clearErrors() {
        _screenState.value = screenState.value.copy(
            phoneNumber = screenState.value.phoneNumber.copy(
                isError = false,
                validateMessage = null
            ),
            password = screenState.value.password.copy(
                isError = false,
                validateMessage = null
            ),
            passwordRepeat = screenState.value.passwordRepeat.copy(
                isError = false,
                validateMessage = null
            ),
        )
    }
}
