package com.projectdelta.jim.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.projectdelta.jim.data.model.Workout
import com.projectdelta.jim.data.model.WorkoutSession
import com.projectdelta.jim.data.model.WorkoutSet
import com.projectdelta.jim.ui.theme.JimTheme
import com.projectdelta.jim.util.Constants.UI.PADDING_NORMAL
import com.projectdelta.jim.util.onClick

/**
 * Component for preview-ing a workout session.
 * @param workoutSession [WorkoutSession] info
 * @param modifier view [Modifier]
 * @param onClickAction single item click listener with [Workout] arg.
 */
@Composable
fun WorkoutSessionComponent(
    workoutSession: WorkoutSession,
    modifier: Modifier,
    onClickAction: onClick<Workout>? = null
){
    LazyColumn(
        modifier = modifier
    ){
        items(workoutSession.workouts) {workout ->
            WorkoutLogComponent(
                workout = workout,
                modifier = Modifier
                    .padding(PADDING_NORMAL)
                ,
                onClickListener = onClickAction
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WorkoutSessionBoxPreview() {
    val session = WorkoutSession(
        timeMs = System.currentTimeMillis(),
        workouts = listOf(
            Workout(
                exerciseName = "Barbell Row",
                sets = List(10){
                    WorkoutSet(
                        weight = 50.0,
                        reps = 10,
                        note = if (it % 2 == 0) "The scope provided to your pager content allows apps to easily reference the" else ""
                    )
                }
            ),
            Workout(
                exerciseName = "Pull Up",
                sets = List(4){
                    WorkoutSet(
                        weight = 80.0,
                        reps = 10,
                        note = if(it % 3 == 0) "The scope provided to your pager content allows apps to easily reference the" else ""
                    )
                }
            ),
            Workout(
                exerciseName = "Seated Cable Row",
                sets = List(3){
                    WorkoutSet(
                        weight = 63.0,
                        reps = 12,
                        note = ""
                    )
                }
            ),
            Workout(
                exerciseName = "Dumbbell Curl",
                sets = List(4){
                    WorkoutSet(
                        weight = 10.0,
                        reps = 12,
                        note = if(it % 4 == 0) "The scope provided to your pager content allows apps to easily reference the" else ""
                    )
                }
            ),
            Workout(
                exerciseName = "Lat Pulldown",
                sets = List(3){
                    WorkoutSet(
                        weight = 80.0,
                        reps = 12,
                    )
                }
            ),
        )
    )
    JimTheme {
        WorkoutSessionComponent(
            workoutSession = session,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        )
    }
}
