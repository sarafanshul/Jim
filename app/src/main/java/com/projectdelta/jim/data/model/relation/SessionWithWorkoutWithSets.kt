package com.projectdelta.jim.data.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSession

data class SessionWithWorkoutWithSets(

    @Embedded
    val session: WorkoutSession,

    @Relation(
        parentColumn = "id",
        entityColumn = "workoutId",
        entity = Workout::class
    )
    val workoutWithSets: List<WorkoutWithSets>
)
