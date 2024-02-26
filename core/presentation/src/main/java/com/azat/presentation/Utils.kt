package com.azat.presentation

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize

@Composable
inline fun <E> MviViewModel<E>.rememberOnEvent(): (E) -> Unit {
    return remember { { this.onEvent(it) } }
}

@Composable
@NonRestartableComposable
fun ColumnScope.SpacerHeight(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
@NonRestartableComposable
fun RowScope.SpacerWidth(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

fun phoneNumberTransform(text: AnnotatedString): TransformedText {
    val mask = "+7 xxx xxx xx xx"
    val annotatedString = AnnotatedString.Builder().run {
        append("+7 ")
        for (i in text.indices) {
            append(text[i])
            when (i) {
                2 -> append(" ")
                5 -> append("-")
                7 -> append("-")
            }
        }
        toAnnotatedString()
    }

    val offsetMapping = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 2) return offset + 3
            if (offset <= 5) return offset + 4
            if (offset <= 7) return offset + 5
            return offset + 6
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset >= 14) return offset - 6
            if (offset >= 11) return offset - 5
            if (offset >= 7) return offset - 4
            if (offset >= 3) return offset - 3
            return 0
        }
    }

    return TransformedText(
        text = annotatedString,
        offsetMapping = offsetMapping
    )
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "shimmer transition")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ),
        label = "shimmer animation"
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFEEEDED),
                Color(0xFFEBE6E6),
                Color(0xFFEEEDED),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier =
    composed {
        this.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            enabled = true,
            onClickLabel = null,
            role = null,
            onClick = onClick
        )
    }

