package com.azat.auth.presentation.registration_confirm.mvi

import com.azat.auth.domain.exceptions.IncorrectCodeException
import com.azat.auth.domain.usecase.ConfirmRegisterUseCase
import com.azat.auth.domain.usecase.ResendCodeUseCase
import com.azat.auth.presentation.AuthNavigator
import com.azat.auth.presentation.AuthScreens
import com.azat.presentation.MviViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class RegistrationConfirmViewModel @Inject constructor(
    private val authNavigator: AuthNavigator,
    private val confirmRegisterUseCase: ConfirmRegisterUseCase,
    private val resendCodeUseCase: ResendCodeUseCase
): MviViewModel<RegistrationConfirmEvent>() {

    private val _screenState = MutableStateFlow(RegistrationConfirmState())
    val screenState: StateFlow<RegistrationConfirmState>
        get() = _screenState.asStateFlow()

    private val _screenEffect = MutableSharedFlow<RegistrationConfirmEffect>()
    val screenEffect: SharedFlow<RegistrationConfirmEffect>
        get() = _screenEffect.asSharedFlow()

    override fun onEvent(event: RegistrationConfirmEvent) {
        when (event) {
            is RegistrationConfirmEvent.OnChangeCode -> onChangeCode(event.value)
            is RegistrationConfirmEvent.OnChangeResentCodeButtonVisibility ->
                onChangeResentCodeButtonVisibility(event.visible)
            is RegistrationConfirmEvent.OnCodeEntered -> onCodeEntered(event.smsId)
            is RegistrationConfirmEvent.OnResentCode -> onResentCode(event.smsId)
        }
    }

    private fun changeLoadingState(value: Boolean) {
        _screenState.value = screenState.value.copy(
            loading = value
        )
    }

    private fun onChangeCode(value: String) {
        _screenState.value = screenState.value.copy(
            code = value
        )
    }

    private fun onChangeResentCodeButtonVisibility(visible: Boolean) {
        _screenState.value = screenState.value.copy(
            isResentCodeButtonVisible = visible
        )
    }

    private fun onCodeEntered(smsId: Int) {
        changeLoadingState(true)
        viewModelScope.launch {
            delay(2000)
            confirmRegisterUseCase(smsId = smsId, code = screenState.value.code).fold(
                onSuccess = {
                    authNavigator.navigateTo(AuthScreens.SuccessfulRegistration)
                },
                onFailure = { exception ->
                    when (exception) {
                        is IncorrectCodeException -> handleIncorrectCode()
                        else -> throw exception
                    }
                }
            )

        }.invokeOnCompletion {
            onChangeCode("")
            changeLoadingState(false)
        }
    }

    private fun handleIncorrectCode() {

    }

    private fun onResentCode(smsId: Int) {
        viewModelScope.launch {
            resendCodeUseCase(smsId)
                .onFailure { exception -> throw exception }
                .onSuccess {

                }
        }
    }
}
