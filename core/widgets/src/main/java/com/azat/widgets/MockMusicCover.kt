package com.azat.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.azat.designsystem.MusicPlayerTheme

@Composable
fun MockMusicCover(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(containerColor)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.music_icon),
            tint = contentColor,
            contentDescription = "music",
            modifier = Modifier.fillMaxSize(.6f)
        )
    }
}

@Composable
@Preview
private fun MockMusicCoverPreview() {
    MusicPlayerTheme {
        MockMusicCover(
            modifier = Modifier
                .size(dimensionResource(id = com.azat.designsystem.R.dimen.list_cover_size))
                .clip(MaterialTheme.shapes.extraSmall)
        )
    }
}
