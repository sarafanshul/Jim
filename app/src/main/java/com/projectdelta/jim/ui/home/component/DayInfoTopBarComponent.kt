package com.projectdelta.jim.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.dp
import com.projectdelta.jim.R
import com.projectdelta.jim.util.TimeUtil
import com.projectdelta.jim.util.unitCallback

/**
 * A top app-bar look alike with two arrows (<, >) and date descriptor.
 * @param currentDay [Int] input for Day element
 * @param onDateClick date click listener
 * @param onBackClick listener for back button
 * @param onNextClick listener for next button
 * @param modifier view [Modifier]
 */
@Composable
fun DayInfoTopBarComponent(
    currentDay: () -> Int,
    onDateClick: unitCallback,
    onBackClick: unitCallback,
    onNextClick: unitCallback,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .weight(9.5f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.round_arrow_back_ios_new_24,
                ),
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f)
                    .fillMaxSize()
                    .clickable(onClick = onBackClick),
                contentDescription = "button day prev"
            )

            Text(
                text = TimeUtil.getDayFormatted(currentDay()),
                modifier = Modifier
                    .weight(8f)
                    .fillMaxSize()
                    .wrapContentHeight()
                    .align(Alignment.CenterVertically)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onDateClick
                    )
                    .semantics { role = Role.Button },
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                style = LocalTextStyle.current.copy(textMotion = TextMotion.Animated)
            )

            Image(
                painter = painterResource(
                    id = R.drawable.round_arrow_forward_ios_24,
                ),
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f)
                    .fillMaxSize()
                    .clickable(onClick = onNextClick),
                contentDescription = "button day next"
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .weight(0.5f),
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp
        )
    }
}
