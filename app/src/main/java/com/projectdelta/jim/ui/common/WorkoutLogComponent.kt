package com.projectdelta.jim.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projectdelta.jim.data.model.Workout
import com.projectdelta.jim.data.model.WorkoutSet
import com.projectdelta.jim.ui.theme.JimTheme
import com.projectdelta.jim.util.Constants.UI.ELEVATION_NORMAL
import com.projectdelta.jim.util.Constants.UI.PADDING_NORMAL
import com.projectdelta.jim.util.Constants.UI.PADDING_SMALL
import com.projectdelta.jim.util.Constants.UI.ROUND_RADIUS_NORMAL
import com.projectdelta.jim.util.Constants.UI.TEXT_NOT_THAT_LARGE
import com.projectdelta.jim.util.Constants.UI.TEXT_SMALL_PLUS
import com.projectdelta.jim.util.onClickWParam

/**
 * Component for Logging [Workout]
 * @param workout [Workout] data
 * @param modifier view [Modifier]
 * @param onClickWParamListener click listener attachment
 */
@Composable
fun WorkoutLogComponent(
    workout: Workout,
    modifier: Modifier,
    onClickWParamListener: onClickWParam<Workout>? = null
) {
    Card(
        shape = RoundedCornerShape(ROUND_RADIUS_NORMAL),
        elevation = CardDefaults.cardElevation(
            defaultElevation = ELEVATION_NORMAL
        ),
        modifier = modifier
            .background(Color.White, RoundedCornerShape(ROUND_RADIUS_NORMAL))
            .fillMaxWidth(),
        onClick = {
            onClickWParamListener?.invoke(workout)
        }
    ) {
        Column {
            Text(
                modifier = Modifier.padding(
                    horizontal = PADDING_NORMAL,
                    vertical = PADDING_SMALL,
                ),
                text = workout.exerciseName,
                fontWeight = FontWeight.Normal,
                fontSize = TEXT_NOT_THAT_LARGE
            )
            Divider(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
            for (set in workout.sets.take(5)) { // take only first 5
                SetLogComponent(
                    modifier = Modifier
                        .padding(0.dp, PADDING_SMALL, 0.dp, 1.dp) // top padding
                        .fillMaxWidth(),
                    set = set
                )
            }
            if (workout.sets.size > 5) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Light,
                                fontSize = TEXT_SMALL_PLUS
                            )
                        ) {
                            append("${workout.sets.size - 5} more")
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = PADDING_NORMAL, 1.dp)
                        .align(Alignment.End)
                )
            }
            Spacer(
                modifier = Modifier
                    .padding(bottom = PADDING_SMALL)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutLogBoxPreview() {
    val log = Workout(
        exerciseName = "Barbell Row",
        sets = List(10) {
            WorkoutSet(
                weight = 50.0,
                reps = 10,
                note = if (it % 2 == 0) "The scope provided to your pager content allows apps to easily reference the" else ""
            )
        }
    )
    JimTheme {
        WorkoutLogComponent(log, Modifier)
    }
}
