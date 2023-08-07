package com.projectdelta.jim.data.model.relation

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.room.Embedded
import androidx.room.Relation
import com.projectdelta.jim.data.model.entity.Exercise
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSet

/**
 * [Relation] class for fetching a [Workout] with it's all [WorkoutSet]
 * and associated [Exercise]
 */
data class WorkoutWithSetsAndExercise(
    @Embedded
    val workout: Workout,

    @Relation(
        parentColumn = "id",
        entityColumn = "workoutId",
        entity = WorkoutSet::class
    )
    val sets: List<WorkoutSet>,

    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "id",
        entity = Exercise::class
    )
    val exercise: Exercise?
)

/**
 * Preview provider for [WorkoutWithSetsAndExercise]
 */
class WWSEParameterProvider : PreviewParameterProvider<WorkoutWithSetsAndExercise> {
    override val values = sequenceOf(
        WorkoutWithSetsAndExercise(
            workout = Workout(0),
            sets = List(6){
                WorkoutSet(
                    weight = 15.0*it,
                    reps = 7 + it
                )
            },
            exercise = Exercise(name = "Romanian Deadlift")
        )
    )
}
