package com.projectdelta.jim.ui.common.widget

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.projectdelta.jim.util.NotFound

/**
 * Not found Filler, displays random emotes on the surface, look for [NotFound]
 */
@Composable
fun NotFoundFiller(
    modifier: Modifier = Modifier,
    text: String = remember { NotFound.surpriseMe() },
    style: TextStyle = MaterialTheme.typography.displayLarge,
    textAlign: TextAlign = TextAlign.Center,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = MaterialTheme.colorScheme.surfaceTint
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
        textAlign = textAlign,
        fontWeight = fontWeight,
        color = color
    )
}
