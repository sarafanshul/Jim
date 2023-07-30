package com.projectdelta.jim

import com.projectdelta.jim.data.model.entity.Exercise
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSession
import com.projectdelta.jim.data.model.entity.WorkoutSet
import com.projectdelta.jim.util.TimeUtil

object TestConstants{
    private const val day = 1000

    val exercise = Exercise(
        name = "Barbell Row",
        force = "pull",
        mechanic = "compound"
    )

    val session = WorkoutSession(
        timeMs = TimeUtil.dayToMilliseconds(day),
        workouts = listOf(
            Workout(
                exerciseName = "Barbell Row",
                sets = List(10) {
                    WorkoutSet(
                        weight = 50.0,
                        reps = 10,
                        note = if (it % 2 == 0) "The scope provided to your pager content allows apps to easily reference the" else ""
                    )
                }
            ),
            Workout(
                exerciseName = "Pull Up",
                sets = List(4) {
                    WorkoutSet(
                        weight = 80.0,
                        reps = 10,
                        note = if (it % 3 == 0) "The scope provided to your pager content allows apps to easily reference the" else ""
                    )
                }
            ),
            Workout(
                exerciseName = "Seated Cable Row",
                sets = List(3) {
                    WorkoutSet(
                        weight = 63.0,
                        reps = 12,
                        note = ""
                    )
                }
            ),
            Workout(
                exerciseName = "Dumbbell Curl",
                sets = List(4) {
                    WorkoutSet(
                        weight = 10.0,
                        reps = 12,
                        note = if (it % 4 == 0) "The scope provided to your pager content allows apps to easily reference the" else ""
                    )
                }
            ),
            Workout(
                exerciseName = "Lat Pulldown",
                sets = List(3) {
                    WorkoutSet(
                        weight = 80.0,
                        reps = 12,
                    )
                }
            ),
        )
    )
}
