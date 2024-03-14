package com.projectdelta.jim.ui.common.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSession
import com.projectdelta.jim.data.model.relation.SWWWParameterProvider
import com.projectdelta.jim.data.model.relation.SessionWithWorkoutWithSets
import com.projectdelta.jim.data.model.relation.WorkoutWithSetsAndExercise
import com.projectdelta.jim.ui.theme.JimTheme
import com.projectdelta.jim.util.Constants.UI.PADDING_NORMAL
import com.projectdelta.jim.util.callbackWParam

/**
 * Component for preview-ing a workout session.
 * @param workoutSession [WorkoutSession] info
 * @param modifier view [Modifier]
 * @param onClickWParamAction single item click listener with [Workout] arg.
 */
@Composable
fun WorkoutSessionComponent(
    workoutSession: SessionWithWorkoutWithSets,
    modifier: Modifier = Modifier,
    onClickWParamAction: callbackWParam<WorkoutWithSetsAndExercise>? = null
) {
    val workoutWithSetAndExercises = remember(workoutSession) {
        workoutSession.workoutWithSetAndExercises
    }
    LazyColumn(
        modifier = modifier
    ) {
        items(
            count = workoutWithSetAndExercises.size,
            key = { idx ->
                workoutWithSetAndExercises[idx].workout.id
            }
        ) itemContent@{ idx ->
            WorkoutLogComponent(
                workout = { workoutWithSetAndExercises[idx] },
                modifier = Modifier
                    .padding(PADDING_NORMAL),
                onClickWParamListener = onClickWParamAction
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    name = "sample session preview", device = "id:pixel_6"
)
@Composable
fun WorkoutSessionComponentPreview(
    @PreviewParameter(SWWWParameterProvider::class)
    workoutSession: SessionWithWorkoutWithSets
) {
    JimTheme {
        Surface {
            WorkoutSessionComponent(
                workoutSession = remember { workoutSession }
            )
        }
    }
}
