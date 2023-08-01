package com.projectdelta.jim.data.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSession

/**
 * Join for [WorkoutSession] && [Workout]
 */
data class SessionWithWorkouts(
    @Embedded
    val session: WorkoutSession,

    @Relation(
        parentColumn = "id",
        entityColumn = "sessionId",
        entity = Workout::class
    )
    val workouts: List<Workout>
)
