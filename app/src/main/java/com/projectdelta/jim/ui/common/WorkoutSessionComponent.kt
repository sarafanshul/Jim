package com.projectdelta.jim.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSession
import com.projectdelta.jim.data.model.relation.SessionWithWorkoutWithSets
import com.projectdelta.jim.data.model.relation.WorkoutWithSetsAndExercise
import com.projectdelta.jim.util.Constants.UI.PADDING_NORMAL
import com.projectdelta.jim.util.onClickWParam

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
    onClickWParamAction: onClickWParam<WorkoutWithSetsAndExercise>? = null
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(workoutSession.workoutWithSetAndExercises) { workout ->
            WorkoutLogComponent(
                workout = workout,
                modifier = Modifier
                    .padding(PADDING_NORMAL),
                onClickWParamListener = onClickWParamAction
            )
        }
    }
}
