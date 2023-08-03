@file:Suppress("unused")

package com.projectdelta.jim.ui.common

import android.graphics.BlurMaskFilter
import android.graphics.BlurMaskFilter.Blur
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Neumorphism Shadow
 * @see [this](https://medium.com/@hanihashemi/implementing-custom-shadows-with-jetpack-compose-for-neumorphism-design-cd666887a642)
 */
fun Modifier.shadow(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
) = then(drawBehind {
    drawIntoCanvas { canvas ->
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        if (blurRadius != 0.dp) {
            frameworkPaint.maskFilter = (BlurMaskFilter(blurRadius.toPx(), Blur.NORMAL))
        }
        frameworkPaint.color = color.toArgb()

        val leftPixel = offsetX.toPx()
        val topPixel = offsetY.toPx()
        val rightPixel = size.width + topPixel
        val bottomPixel = size.height + leftPixel

        canvas.drawRect(
            left = leftPixel,
            top = topPixel,
            right = rightPixel,
            bottom = bottomPixel,
            paint = paint,
        )
    }
})

/**
 * A modifier for handling hold press, i.e it'll call [onClick] constantly till Down.pressed
 *
 * @see <a href="https://proandroiddev.com/creating-a-repeating-button-with-jetpack-compose-b39c4f559f7d">for more info</a>
 */
fun Modifier.repeatingClickable(
    interactionSource: InteractionSource,
    enabled: Boolean = true,
    maxDelayMillis: Long = 500,
    minDelayMillis: Long = 60,
    delayDecayFactor: Float = .20f,
    onClick: () -> Unit
): Modifier = composed {

    val currentClickListener by rememberUpdatedState(onClick)
    val isEnabled by rememberUpdatedState(enabled)
    val scope = rememberCoroutineScope { Dispatchers.IO }

    pointerInput(interactionSource, enabled) {
        awaitEachGesture {
            val down = awaitFirstDown(requireUnconsumed = false)
            val heldButtonJob = scope.launch {
                var currentDelayMillis = maxDelayMillis
                while (isEnabled && down.pressed) {
                    currentClickListener()
                    delay(currentDelayMillis)
                    val nextMillis =
                        currentDelayMillis - (currentDelayMillis * delayDecayFactor)
                    currentDelayMillis = nextMillis.toLong().coerceAtLeast(minDelayMillis)
                }
            }
            waitForUpOrCancellation()
            heldButtonJob.cancel()
        }
    }
}
