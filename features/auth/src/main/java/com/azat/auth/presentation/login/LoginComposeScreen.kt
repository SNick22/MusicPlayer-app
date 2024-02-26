package com.azat.auth.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.azat.auth.R
import com.azat.auth.presentation.login.mvi.LoginEvent
import com.azat.auth.presentation.login.mvi.LoginState
import com.azat.auth.presentation.login.mvi.LoginViewModel
import com.azat.auth.presentation.widgets.PrimaryButton
import com.azat.common_impl.asString
import com.azat.designsystem.MusicPlayerTheme
import com.azat.presentation.SpacerHeight
import com.azat.presentation.SpacerWidth
import com.azat.presentation.phoneNumberTransform
import com.azat.presentation.rememberOnEvent
import com.azat.widgets.text_field.MusicPlayerTextField

@Composable
internal fun LoginComposeScreen(
    viewModel: LoginViewModel
) {

    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val screenEffect by viewModel.screenEffect.collectAsStateWithLifecycle(null)

    LaunchedEffect(screenEffect) {

    }

    LoginContent(
        screenState = screenState,
        onEvent = viewModel.rememberOnEvent()
    )
}

@Composable
private fun LoginContent(
    screenState: LoginState,
    onEvent: (LoginEvent) -> Unit
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
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.headlineLarge
            )

            SpacerHeight(height = 16.dp)

            Row {
                Text(
                    text = stringResource(R.string.first_visit),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                SpacerWidth(width = 4.dp)

                Text(
                    text = stringResource(R.string.to_register),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = remember {
                        Modifier.clickable { onEvent(LoginEvent.OnGoRegistration) }
                    }
                )
            }

            SpacerHeight(height = 24.dp)

            MusicPlayerTextField(
                value = screenState.phoneNumber.value,
                onValueChange = { onEvent(LoginEvent.OnChangePhoneNumber(it)) },
                label = { Text(text = stringResource(R.string.phone_number_label)) },
                isError = screenState.phoneNumber.isError,
                supportingText = {
                    screenState.phoneNumber.validateMessage?.let { Text(text = it.asString()) }
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = { phoneNumberTransform(it) }
            )

            SpacerHeight(height = 16.dp)

            MusicPlayerTextField(
                value = screenState.password.value,
                onValueChange = { onEvent(LoginEvent.OnChangePassword(it)) },
                label = { Text(text = stringResource(R.string.password_label)) },
                isError = screenState.password.isError,
                supportingText = {
                    screenState.password.validateMessage?.let { Text(text = it.asString()) }
                },
                placeholder = { Text(text = stringResource(R.string.password_placeholder)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onEvent(LoginEvent.OnLogin) }
                )
            )

            SpacerHeight(height = 24.dp)

            PrimaryButton(
                onClick = { onEvent(LoginEvent.OnLogin) },
                isLoading = screenState.loading
            ) {
                Text(text = stringResource(R.string.to_login))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun LoginPreview() {
    MusicPlayerTheme {
        LoginContent(
            screenState = LoginState(loading = true),
            onEvent = {}
        )
    }
}
