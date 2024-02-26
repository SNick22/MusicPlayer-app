package com.azat.auth.presentation.registration

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.azat.auth.R
import com.azat.auth.presentation.login.mvi.LoginEvent
import com.azat.auth.presentation.registration.mvi.RegistrationEffect
import com.azat.auth.presentation.registration.mvi.RegistrationEvent
import com.azat.auth.presentation.registration.mvi.RegistrationState
import com.azat.auth.presentation.registration.mvi.RegistrationViewModel
import com.azat.auth.presentation.widgets.PrimaryButton
import com.azat.common_impl.asString
import com.azat.designsystem.MusicPlayerTheme
import com.azat.presentation.SpacerHeight
import com.azat.presentation.phoneNumberTransform
import com.azat.presentation.rememberOnEvent
import com.azat.widgets.text_field.MusicPlayerTextField

@Composable
internal fun RegistrationComposeScreen(
    viewModel: RegistrationViewModel
) {

    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val screenEffect = viewModel.screenEffect.collectAsStateWithLifecycle(null)

    val context = LocalContext.current

    LaunchedEffect(screenEffect.value) {
        when (val effect = screenEffect.value) {
            is RegistrationEffect.ShowToast -> {
                Toast.makeText(
                    context,
                    effect.text.asString(context.resources),
                    Toast.LENGTH_LONG
                ).show()
            }
            null -> Unit
        }
    }

    RegistrationContent(
        screenState = screenState,
        onEvent = viewModel.rememberOnEvent()
    )
}

@Composable
private fun RegistrationContent(
    screenState: RegistrationState,
    onEvent: (RegistrationEvent) -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .imePadding()
                .verticalScroll(state = scrollState)
                .padding(
                    horizontal = dimensionResource(
                        id = com.azat.designsystem.R.dimen.medium_container_padding
                    )
                )
        ) {
            Text(
                text = stringResource(R.string.registration),
                style = MaterialTheme.typography.headlineLarge
            )

            SpacerHeight(height = 24.dp)

            MusicPlayerTextField(
                value = screenState.phoneNumber.value,
                onValueChange = { onEvent(RegistrationEvent.OnChangePhoneNumber(it)) },
                label = { Text(text = stringResource(R.string.phone_number_label)) },
                isError = screenState.phoneNumber.isError,
                supportingText = {
                    screenState.phoneNumber.validateMessage?.let { Text(text = it.asString()) }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = { phoneNumberTransform(it) }
            )

            SpacerHeight(height = 16.dp)

            MusicPlayerTextField(
                value = screenState.password.value,
                onValueChange = { onEvent(RegistrationEvent.OnChangePassword(it)) },
                label = { Text(text = stringResource(R.string.password_label)) },
                isError = screenState.password.isError,
                supportingText = {
                    screenState.password.validateMessage?.let { Text(text = it.asString()) }
                },
                placeholder = { Text(text = stringResource(R.string.password_placeholder)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
            )

            SpacerHeight(height = 16.dp)

            MusicPlayerTextField(
                value = screenState.passwordRepeat.value,
                onValueChange = { onEvent(RegistrationEvent.OnChangePasswordRepeat(it)) },
                label = { Text(text = stringResource(R.string.password_repeat_label)) },
                isError = screenState.passwordRepeat.isError,
                supportingText = {
                    screenState.passwordRepeat.validateMessage?.let { Text(text = it.asString()) }
                },
                placeholder = { Text(text = stringResource(R.string.password_repeat_password)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onEvent(RegistrationEvent.OnRegister) }
                )
            )

            SpacerHeight(height = 24.dp)

            PrimaryButton(
                onClick = { onEvent(RegistrationEvent.OnRegister) },
                isLoading = screenState.loading
            ) {
                Text(text = stringResource(R.string.to_register))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun RegistrationPreview() {
    MusicPlayerTheme {
        RegistrationContent(
            screenState = RegistrationState(),
            onEvent = {}
        )
    }
}
