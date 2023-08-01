package com.projectdelta.jim.data.repository

import androidx.paging.PagingData
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSet
import com.projectdelta.jim.data.model.relation.WorkoutWithSetsAndExercise
import com.projectdelta.jim.util.BaseId
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository : BaseDBRepository<Workout> {

    /**
     * Fetches [WorkoutSet] & [Workout] managed by [WorkoutWithSetsAndExercise] relation
     */
    fun getWorkoutWithSetsAndExerciseById(id: BaseId): Flow<List<WorkoutWithSetsAndExercise>>

    /**
     * Fetches all [WorkoutWithSetsAndExercise] from `WORKOUT_TABLE`
     * @return [List] of all [WorkoutWithSetsAndExercise]
     */
    fun getAllWorkoutWithSetsAndExercise(): Flow<List<WorkoutWithSetsAndExercise>>

    /**
     * Fetches all [WorkoutWithSetsAndExercise] from `WORKOUT_TABLE`, Paged, ORDER_BY ASC
     * @return [PagingData]<[WorkoutWithSetsAndExercise]>
     */
    fun getAllWorkoutWithSetsAndExercisePaged(): Flow<PagingData<WorkoutWithSetsAndExercise>>
}
