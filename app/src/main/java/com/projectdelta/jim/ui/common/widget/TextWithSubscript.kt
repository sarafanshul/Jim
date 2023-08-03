package com.projectdelta.jim.ui.common.widget

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import com.projectdelta.jim.util.Constants

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
) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = fontSizeNormal,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.Monospace
                )
            ) {
                append(textNormal)
            }
            append(" ")
            withStyle(
                style = SpanStyle(
                    fontSize = fontSizeSubscript,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Monospace
                )
            ) {
                append(textSubscript)
            }
        },
        modifier = modifier,
    )
}
