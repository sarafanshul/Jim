package com.projectdelta.jim.ui.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.projectdelta.jim.util.Constants
import com.projectdelta.jim.util.unitCallback
import com.projectdelta.jim.util.deferredRead

/**
 * Image clickable card with transparent background and a text bellow it
 * @param text display text
 * @param painter [Painter] object with resource info
 * @param onClick click event listener
 * @param modifier view's [Modifier]
 * @param contentDescription bruh :(
 */
@Composable
fun ImageButtonWithText(
    text: deferredRead<String>,
    painter: Painter,
    onClick: unitCallback,
    modifier: Modifier = Modifier,
    contentDescription: deferredRead<String> = { "" }
) {
    Card(
        shape = RoundedCornerShape(Constants.UI.ROUND_RADIUS_NORMAL),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        modifier = modifier
            .background(Color.Transparent, RoundedCornerShape(Constants.UI.ROUND_RADIUS_NORMAL)),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = Constants.UI.PADDING_SMALL,
                    vertical = Constants.UI.PADDING_NORMAL
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painter,
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary
                ),
                contentDescription = contentDescription(),
                modifier = Modifier
                    .weight(7f)
                    .fillMaxSize()
            )
            Text(
                text = text(),
                fontWeight = FontWeight.Light,
                fontSize = Constants.UI.TEXT_SMALL_PLUS,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(3f)
            )
        }
    }
}
