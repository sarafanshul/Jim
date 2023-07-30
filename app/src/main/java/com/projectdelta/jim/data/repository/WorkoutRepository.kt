package com.projectdelta.jim.data.repository

import androidx.paging.PagingData
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSet
import com.projectdelta.jim.data.model.relation.WorkoutWithSets
import com.projectdelta.jim.util.BaseId
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository : BaseDBRepository<Workout> {

    /**
     * Fetches [WorkoutSet] & [Workout] managed by [WorkoutWithSets] relation
     */
    fun getWorkoutWithSetsById(id: BaseId): Flow<List<WorkoutWithSets>>

    /**
     * Fetches all [WorkoutWithSets] from `WORKOUT_TABLE`
     * @return [List] of all [WorkoutWithSets]
     */
    fun getAllWorkoutWithSets(): Flow<List<WorkoutWithSets>>

    /**
     * Fetches all [WorkoutWithSets] from `WORKOUT_TABLE`, Paged, ORDER_BY ASC
     * @return [PagingData]<[WorkoutWithSets]>
     */
    fun getAllWorkoutWithSetsPaged(): Flow<PagingData<WorkoutWithSets>>
}
