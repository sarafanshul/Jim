package com.projectdelta.jim.data.repository

import com.projectdelta.jim.data.model.entity.WorkoutSet
import com.projectdelta.jim.data.model.relation.WorkoutSetAndWorkout
import com.projectdelta.jim.util.BaseId
import kotlinx.coroutines.flow.Flow

interface WorkoutSetRepository : BaseDBRepository<WorkoutSet> {

    /**
     * Fetches [WorkoutSet] mapped with it's parent Workout
     */
    suspend fun getWorkoutSetAndWorkoutById(id: BaseId) : Flow<List<WorkoutSetAndWorkout>>
}
