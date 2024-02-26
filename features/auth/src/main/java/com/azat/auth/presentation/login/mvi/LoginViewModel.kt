package com.azat.auth.presentation.login.mvi

import com.azat.auth.AuthFeatureNavigator
import com.azat.auth.domain.exceptions.IncorrectPhoneOrPasswordException
import com.azat.auth.domain.exceptions.InvalidPasswordException
import com.azat.auth.domain.exceptions.InvalidPhoneException
import com.azat.auth.domain.usecase.LoginUseCase
import com.azat.auth.domain.usecase.SaveTokenUseCase
import com.azat.auth.presentation.AuthNavigator
import com.azat.auth.presentation.AuthScreens
import com.azat.common_impl.UiText
import com.azat.presentation.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val authNavigator: AuthNavigator,
    private val authFeatureNavigator: AuthFeatureNavigator,
    private val loginUseCase: LoginUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
): MviViewModel<LoginEvent>() {

    private val _screenState = MutableStateFlow(LoginState())
    val screenState: StateFlow<LoginState>
        get() = _screenState.asStateFlow()

    private val _screenEffect = MutableSharedFlow<LoginEffect>()
    val screenEffect: SharedFlow<LoginEffect>
        get() = _screenEffect.asSharedFlow()

    private fun changeLoadingState(value: Boolean) {
        _screenState.value = screenState.value.copy(
            loading = value
        )
    }

    override fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.OnGoRegistration -> onGoRegistration()
            LoginEvent.OnLogin -> onLogin()
            is LoginEvent.OnChangePassword -> onChangePassword(event.value)
            is LoginEvent.OnChangePhoneNumber -> onChangePhoneNumber(event.value)
        }
    }

    private fun onGoRegistration() {
        viewModelScope.launch {
            authNavigator.navigateTo(screen = AuthScreens.Registration)
        }
    }

    private fun onChangePassword(value: String) {
        _screenState.value = screenState.value.copy(
            password = screenState.value.password.copy(value = value)
        )
    }

    private fun onChangePhoneNumber(value: String) {
        _screenState.value = screenState.value.copy(
            phoneNumber = screenState.value.phoneNumber.copy(value = value)
        )
    }

    private fun onLogin() {
        changeLoadingState(true)
        viewModelScope.launch {
            loginUseCase(
                phoneNumber = screenState.value.phoneNumber.value,
                password = screenState.value.password.value
            ).fold(
                onSuccess = { token ->
                    saveTokenUseCase(token)
                    authFeatureNavigator.navigateToHomeFeature()
                },
                onFailure = { exception ->
                    clearErrors()
                    when(exception) {
                        is InvalidPhoneException -> handleInvalidPhone(exception.errorMessage)
                        is InvalidPasswordException -> handleInvalidPassword(exception.errorMessage)
                        is IncorrectPhoneOrPasswordException ->
                            handleIncorrectPhoneOrPassword(exception.errorMessage)
                        else -> throw exception
                    }
                }
            )
        }.invokeOnCompletion {
            changeLoadingState(false)
        }
    }

    private fun handleIncorrectPhoneOrPassword(message: UiText) {
        _screenState.value = screenState.value.copy(
            phoneNumber = screenState.value.phoneNumber.copy(
                isError = true
            ),
            password = screenState.value.password.copy(
                isError = true,
                validateMessage = message
            ),
        )
    }

    private fun handleInvalidPassword(message: UiText) {
        _screenState.value = screenState.value.copy(
            password = screenState.value.password.copy(
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
        )
    }
}
