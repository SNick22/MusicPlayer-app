package com.azat.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.azat.designsystem.R

@Composable
fun CoilImage(
    photoUrl: String,
    modifier: Modifier = Modifier,
    onState: ((AsyncImagePainter.State) -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.extraSmall,
) {
    AsyncImage(
        model = photoUrl,
        contentDescription = null,
        onState = onState,
        modifier = modifier
            .clip(shape)
    )
}
