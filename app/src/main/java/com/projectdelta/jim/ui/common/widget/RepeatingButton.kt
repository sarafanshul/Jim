package com.projectdelta.jim.ui.common.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.projectdelta.jim.ui.common.repeatingClickable

/**
 * Button to handle hold/press events
 * @see <a href="https://proandroiddev.com/creating-a-repeating-button-with-jetpack-compose-b39c4f559f7d">for more info</a>
 */
@Composable
fun RepeatingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    maxDelayMillis: Long = 1000,
    minDelayMillis: Long = 5,
    delayDecayFactor: Float = .20f,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier.repeatingClickable(
            interactionSource = interactionSource,
            enabled = enabled,
            maxDelayMillis = maxDelayMillis,
            minDelayMillis = minDelayMillis,
            delayDecayFactor = delayDecayFactor
        ) { onClick() },
        onClick = {},
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
        content = content
    )
}

/**
 * Icon Button to handle hold/press events
 * @see <a href="https://proandroiddev.com/creating-a-repeating-button-with-jetpack-compose-b39c4f559f7d">for more info</a>
 */
@Composable
fun RepeatingIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    maxDelayMillis: Long = 500,
    minDelayMillis: Long = 60,
    delayDecayFactor: Float = .20f,
    content: @Composable () -> Unit
) {
    IconButton(
        modifier = modifier.repeatingClickable(
            interactionSource = interactionSource,
            enabled = enabled,
            maxDelayMillis = maxDelayMillis,
            minDelayMillis = minDelayMillis,
            delayDecayFactor = delayDecayFactor,
            onClick = onClick,
        ),
        onClick = {},
        enabled = enabled,
        interactionSource = interactionSource,
        colors = colors,
        content = content
    )
}
