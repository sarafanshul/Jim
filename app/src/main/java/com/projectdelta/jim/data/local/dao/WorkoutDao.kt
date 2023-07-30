package com.projectdelta.jim.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSession
import com.projectdelta.jim.data.model.entity.WorkoutSet
import com.projectdelta.jim.data.model.relation.WorkoutWithSets
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.util.Constants.Table.WORKOUT_TABLE
import kotlinx.coroutines.flow.Flow

/**
 * [Dao] for [Workout] Table.
 */
@Dao
interface WorkoutDao : BaseDao<Workout> {

    /**
     * Fetches [WorkoutSession] by id
     * @param id unique id of item to fetch
     * @return [WorkoutSession] if found.
     */
    @Query("SELECT * FROM $WORKOUT_TABLE WHERE id = :id")
    fun getById(id: BaseId): Flow<Workout?>

    /**
     * Fetches all [Workout] from `WORKOUT_TABLE`
     * @return [List] of all [Workout]
     */
    @Query("SELECT * FROM $WORKOUT_TABLE")
    fun getAllWorkouts(): Flow<List<Workout>>

    /**
     * Fetches all [WorkoutSession] from `WORKOUT_TABLE`
     * @return [PagingSource]<[Int],[WorkoutSession]>
     */
    @Query("SELECT * FROM $WORKOUT_TABLE")
    fun getAllWorkoutsPaged(): PagingSource<Int, Workout>

    /**
     * Fetches [WorkoutSet] & [Workout] managed by [WorkoutWithSets] relation
     */
    @Query("SELECT * FROM $WORKOUT_TABLE WHERE id = :id")
    fun getWorkoutWithSetsById(id: BaseId): Flow<List<WorkoutWithSets>>

    /**
     * Fetches all [WorkoutWithSets] from `WORKOUT_TABLE`
     * @return [List] of all [WorkoutWithSets]
     */
    @Query("SELECT * FROM $WORKOUT_TABLE")
    fun getAllWorkoutWithSets(): Flow<List<WorkoutWithSets>>

    /**
     * Fetches all [WorkoutWithSets] from `WORKOUT_TABLE`
     * @return [PagingSource]<[Int],[WorkoutWithSets]>
     */
    @Query("SELECT * FROM $WORKOUT_TABLE")
    fun getAllWorkoutWithSetsPaged(): Flow<PagingSource<Int, WorkoutWithSets>>
}
