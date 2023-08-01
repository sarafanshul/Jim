package com.projectdelta.jim.data.model.relation

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.room.Embedded
import androidx.room.Relation
import com.projectdelta.jim.data.model.entity.Exercise
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSession
import com.projectdelta.jim.data.model.entity.WorkoutSet

/**
 * Relation class for [WorkoutSession] and it's all
 * associated [Workout], WorkoutSet, Exercise in form of
 * [WorkoutWithSetsAndExercise] mapped
 */
data class SessionWithWorkoutWithSets(

    @Embedded
    val session: WorkoutSession,

    @Relation(
        parentColumn = "id",
        entityColumn = "sessionId",
        entity = Workout::class
    )
    val workoutWithSetAndExercises: List<WorkoutWithSetsAndExercise>
)

/**
 * Preview provider for [SessionWithWorkoutWithSets]
 */
class SWWWParameterProvider : PreviewParameterProvider<SessionWithWorkoutWithSets> {
    override val values = sequenceOf(
        SessionWithWorkoutWithSets(
            session = WorkoutSession(0),
            workoutWithSetAndExercises = List(7) {
                WorkoutWithSetsAndExercise(
                    workout = Workout(),
                    sets = generateSequence(WorkoutSet(reps = 7)) {
                        WorkoutSet(reps = it.reps + 1)
                    }.take(7).toList(),
                    exercise = Exercise(name = "test exercise $it")
                )
            }
        )
    )
}
