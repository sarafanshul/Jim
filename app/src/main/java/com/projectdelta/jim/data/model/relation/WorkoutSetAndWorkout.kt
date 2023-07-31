package com.projectdelta.jim.data.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSet

/**
 * Relation class for [WorkoutSet] and it's parent [Workout]
 */
data class WorkoutSetAndWorkout(
    @Embedded
    val workoutSet: WorkoutSet,

    @Relation(
        parentColumn = "workoutId",
        entityColumn = "id",
        entity = Workout::class
    )
    val workout: Workout?

)
