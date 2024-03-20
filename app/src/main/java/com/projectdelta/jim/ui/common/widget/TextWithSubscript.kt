package com.projectdelta.jim.ui.common.widget

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import com.projectdelta.jim.ui.theme.Dimens.TEXT_MEDIUM
import com.projectdelta.jim.ui.theme.Dimens.TEXT_SMALL
import com.projectdelta.jim.util.Constants

/**
 * Text with subscript
 */
@Composable
fun TextWithSubscript(
    textNormal: String,
    textSubscript: String,
    modifier: Modifier = Modifier,
    fontSizeNormal: TextUnit = TEXT_MEDIUM,
    fontSizeSubscript: TextUnit = TEXT_SMALL,
    textAlign: TextAlign = TextAlign.Center,
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
                    fontFamily = FontFamily.Monospace,
                )
            ) {
                append(textSubscript)
            }
        },
        modifier = modifier,
        textAlign = textAlign,
        maxLines = 1,
        softWrap = true,
        overflow = TextOverflow.Ellipsis,
    )
}
