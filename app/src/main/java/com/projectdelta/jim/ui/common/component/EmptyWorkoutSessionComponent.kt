package com.projectdelta.jim.ui.common.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.projectdelta.jim.R
import com.projectdelta.jim.data.state.SessionState
import com.projectdelta.jim.util.Constants
import com.projectdelta.jim.util.unitCallback

/**
 * Component screen to display when no workout is found ,[SessionState.Empty] state,
 * @param startNewWorkoutOnClick listener for start new workout Button
 * @param copyPreviousWorkoutOnClick listener for copy previous button
 * @param modifier view [Modifier]
 */
@Composable
fun EmptyWorkoutSessionComponent(
    startNewWorkoutOnClick: unitCallback,
    copyPreviousWorkoutOnClick: unitCallback,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = Constants.UI.TEXT_LARGE
                    )
                ) {
                    append(Constants.StringRes.NO_WORKOUT_LOG)
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .weight(8f),
            textAlign = TextAlign.Center
        )

        ImageButtonWithText(
            text = { "Start New Workout" },
            painter = painterResource(
                id = R.drawable.baseline_add_24
            ),
            onClick = startNewWorkoutOnClick,
            modifier = Modifier
                .weight(1.2f)
                .fillMaxWidth(0.5f)
                .padding(Constants.UI.PADDING_SMALL, Constants.UI.PADDING_SMALL)
        )

        Spacer(
            modifier = Modifier
                .weight(0.15f)
        )

        ImageButtonWithText(
            text = { "Copy Previous Workout" },
            painter = painterResource(
                id = R.drawable.baseline_content_copy_24
            ),
            onClick = copyPreviousWorkoutOnClick,
            modifier = Modifier
                .weight(1.2f)
                .fillMaxWidth(0.5f)
                .padding(Constants.UI.PADDING_SMALL, Constants.UI.PADDING_SMALL)
        )
    }
}
