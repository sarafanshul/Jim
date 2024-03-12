package com.projectdelta.jim.ui.common.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.relation.WWSEParameterProvider
import com.projectdelta.jim.data.model.relation.WorkoutWithSetsAndExercise
import com.projectdelta.jim.ui.theme.JimTheme
import com.projectdelta.jim.util.Constants.UI.ELEVATION_NORMAL
import com.projectdelta.jim.util.Constants.UI.PADDING_NORMAL
import com.projectdelta.jim.util.Constants.UI.PADDING_SMALL
import com.projectdelta.jim.util.Constants.UI.ROUND_RADIUS_NORMAL
import com.projectdelta.jim.util.Constants.UI.TEXT_NOT_THAT_LARGE
import com.projectdelta.jim.util.Constants.UI.TEXT_SMALL_PLUS
import com.projectdelta.jim.util.NotFound
import com.projectdelta.jim.util.callbackWParam


/**
 * Component for Logging [Workout]
 * @param workout [Workout] data
 * @param modifier view [Modifier]
 * @param onClickWParamListener click listener attachment
 */
@Composable
fun WorkoutLogComponent(
    workout: () -> WorkoutWithSetsAndExercise,
    modifier: Modifier = Modifier,
    onClickWParamListener: callbackWParam<WorkoutWithSetsAndExercise>? = null
) {
    // hardcoded color for now
    val background = MaterialTheme.colorScheme.primaryContainer

    Column(
        modifier = modifier
            .shadow(
                elevation = ELEVATION_NORMAL,
                shape = RoundedCornerShape(ROUND_RADIUS_NORMAL),
                clip = true
            )
            .fillMaxWidth()
            .drawBehind {
                drawRect(
                    color = background
                )
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClickWParamListener?.invoke(workout()) }
            ),
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = PADDING_NORMAL,
                vertical = PADDING_SMALL,
            ),
            text = workout().exercise?.name ?: NotFound.surpriseMe(),
            fontWeight = FontWeight.Normal,
            fontSize = TEXT_NOT_THAT_LARGE,
            maxLines = 1,
            softWrap = true,
            overflow = TextOverflow.Ellipsis,
        )
        Divider(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
        for (set in workout().sets.take(5)) { // take only first 5
            SetLogComponent(
                modifier = Modifier
                    .padding(0.dp, PADDING_SMALL, 0.dp, 1.dp) // top padding
                    .fillMaxWidth(),
                setWeight = { set.weight },
                setReps = { set.reps },
                setNote = { set.note },
            )
        }
        if (workout().sets.size > 5) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Light,
                            fontSize = TEXT_SMALL_PLUS
                        )
                    ) {
                        append("${workout().sets.size - 5} more")
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

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun WorkoutLogComponentPreview(
    @PreviewParameter(WWSEParameterProvider::class, limit = 1)
    workout: WorkoutWithSetsAndExercise,
) {
    JimTheme {
        Surface {
            WorkoutLogComponent(workout = { workout })
        }
    }
}

@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun WorkoutLogComponentDarkModePreview(
    @PreviewParameter(WWSEParameterProvider::class, limit = 1)
    workout: WorkoutWithSetsAndExercise,
) {
    JimTheme {
        Surface {
            WorkoutLogComponent(workout = { workout })
        }
    }
}
