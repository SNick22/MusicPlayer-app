package com.azat.auth.presentation.successful_registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.azat.auth.R
import com.azat.auth.presentation.login.mvi.LoginEvent
import com.azat.auth.presentation.successful_registration.mvi.SuccessfulRegistrationEvent
import com.azat.auth.presentation.successful_registration.mvi.SuccessfulRegistrationViewModel
import com.azat.designsystem.MusicPlayerTheme
import com.azat.presentation.SpacerHeight
import com.azat.presentation.rememberOnEvent

@Composable
internal fun SuccessfulRegistrationComposeScreen(
    viewModel: SuccessfulRegistrationViewModel
) {

    SuccessfulRegistrationContent(
        onEvent = viewModel.rememberOnEvent()
    )
}

@Composable
private fun SuccessfulRegistrationContent(
    onEvent: (SuccessfulRegistrationEvent) -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(state = scrollState)
                .padding(
                    horizontal = dimensionResource(
                        id = com.azat.designsystem.R.dimen.medium_container_padding
                    )
                )
        ) {
            
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.successful_registration),
                contentDescription = "successful registration",
                modifier = Modifier.size(160.dp)
            )

            SpacerHeight(height = 64.dp)

            Text(
                text = stringResource(id = R.string.successful_registration),
                style = MaterialTheme.typography.titleLarge
            )

            SpacerHeight(height = 16.dp)

            Text(
                text = stringResource(id = R.string.successful_registration_desc),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            SpacerHeight(height = 32.dp)

            Button(
                onClick = { onEvent(SuccessfulRegistrationEvent.OnNavigateToLogin) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                contentPadding = PaddingValues(vertical = 22.dp)
            ) {
                Text(text = stringResource(R.string.go_to_login))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SuccessfulRegistrationPreview() {
    MusicPlayerTheme {
        SuccessfulRegistrationContent(onEvent = {})
    }
}
