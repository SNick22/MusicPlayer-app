package com.azat.auth.presentation.registration_confirm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.azat.auth.R
import com.azat.auth.presentation.registration_confirm.mvi.RegistrationConfirmEvent
import com.azat.auth.presentation.registration_confirm.mvi.RegistrationConfirmState
import com.azat.auth.presentation.registration_confirm.mvi.RegistrationConfirmViewModel
import com.azat.common.formatToMinutesSeconds
import com.azat.designsystem.MusicPlayerTheme
import com.azat.presentation.SpacerHeight
import com.azat.presentation.SpacerWidth
import com.azat.presentation.rememberOnEvent
import com.azat.widgets.CountDownTimer
import com.azat.widgets.code_text_field.CodeTextField
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun RegistrationConfirmComposeScreen(
    smsId: Int,
    viewModel: RegistrationConfirmViewModel
) {

    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val screenEffect by viewModel.screenEffect.collectAsStateWithLifecycle(null)

    LaunchedEffect(screenEffect) {

    }

    RegistrationConfirmContent(
        smsId = smsId,
        screenState = screenState,
        onEvent = viewModel.rememberOnEvent()
    )
}

@Composable
private fun RegistrationConfirmContent(
    smsId: Int,
    screenState: RegistrationConfirmState,
    onEvent: (RegistrationConfirmEvent) -> Unit
) {
    if (!screenState.loading) {
        MainContent(
            smsId = smsId,
            screenState = screenState,
            onEvent = onEvent
        )
    } else {
        LoadingContent()
    }
}

@Composable
private fun LoadingContent() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun MainContent(
    smsId: Int,
    screenState: RegistrationConfirmState,
    onEvent: (RegistrationConfirmEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

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
                text = stringResource(id = R.string.phone_number_confirmation),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            SpacerHeight(height = 16.dp)

            Text(
                text = stringResource(id = R.string.phone_number_confirmation_desc),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            SpacerHeight(height = 24.dp)

            CodeTextField(
                codeLength = screenState.codeLength,
                value = screenState.code,
                placeholder = "0000",
                modifier = Modifier
                    .focusRequester(focusRequester),
                onValueChange = { onEvent(RegistrationConfirmEvent.OnChangeCode(it)) },
                onEnterComplete = { onEvent(RegistrationConfirmEvent.OnCodeEntered(smsId)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                )
            )

            SpacerHeight(height = 24.dp)

            if (screenState.isResentCodeButtonVisible) {
                TextButton(
                    onClick = {
                        onEvent(RegistrationConfirmEvent.OnResentCode(smsId))
                        onEvent(RegistrationConfirmEvent.OnChangeResentCodeButtonVisibility(false))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                    contentPadding = PaddingValues(vertical = 10.dp)
                ) {
                    Text(text = stringResource(id = R.string.resent_code))
                }
            } else {
                Row {
                    Text(
                        text = stringResource(id = R.string.resent_code_in),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    SpacerWidth(width = 4.dp)

                    CountDownTimer(
                        initialTimeSeconds = screenState.timerInitialSeconds,
                        intervalSeconds = 1L,
                        onFinishTimer = {
                            onEvent(
                                RegistrationConfirmEvent
                                    .OnChangeResentCodeButtonVisibility(true)
                            )
                        }
                    ) { time ->
                        Text(
                            text = time.seconds.formatToMinutesSeconds(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun RegistrationConfirmPreview() {
    MusicPlayerTheme {
        RegistrationConfirmContent(
            smsId = 0,
            screenState = RegistrationConfirmState(),
            onEvent = {}
        )
    }
}
