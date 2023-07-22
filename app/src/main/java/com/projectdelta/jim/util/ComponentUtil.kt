@file:Suppress("unused")

package com.projectdelta.jim.util

import android.graphics.BlurMaskFilter
import android.graphics.BlurMaskFilter.Blur
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

/**
 * Neumorphism Shadow
 * @see [Blog](https://medium.com/@hanihashemi/implementing-custom-shadows-with-jetpack-compose-for-neumorphism-design-cd666887a642)
 */
fun Modifier.shadow(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
) = then(
    drawBehind {
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
    }
)

/**
 * Text with subscript
 */
@Composable
fun TextWithSubscript(
    textNormal: String,
    textSubscript: String,
    modifier: Modifier = Modifier,
    fontSizeNormal: TextUnit = Constants.UI.TEXT_MEDIUM,
    fontSizeSubscript: TextUnit = Constants.UI.TEXT_SMALL,
){
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(
                fontSize = fontSizeNormal,
            )
            ) {
                append(textNormal)
            }
            append(" ")
            withStyle(style = SpanStyle(
                fontSize = fontSizeSubscript,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Light
            )
            ) {
                append(textSubscript)
            }
        },
        modifier = modifier
    )
}
