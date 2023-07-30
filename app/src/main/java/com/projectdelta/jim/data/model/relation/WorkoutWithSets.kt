package com.projectdelta.jim.data.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.projectdelta.jim.data.model.entity.Exercise
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSet

data class WorkoutWithSets(
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
    val exercise: Exercise
)
