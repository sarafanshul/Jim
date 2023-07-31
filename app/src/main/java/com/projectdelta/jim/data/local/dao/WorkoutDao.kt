package com.projectdelta.jim.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSession
import com.projectdelta.jim.data.model.entity.WorkoutSet
import com.projectdelta.jim.data.model.relation.WorkoutWithSetsAndExercise
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
     * Fetches [WorkoutSet] & [Workout] managed by [WorkoutWithSetsAndExercise] relation
     */
    @Query("SELECT * FROM $WORKOUT_TABLE WHERE id = :id")
    fun getWorkoutWithSetsAndExerciseById(id: BaseId): Flow<List<WorkoutWithSetsAndExercise>>

    /**
     * Fetches all [WorkoutWithSetsAndExercise] from `WORKOUT_TABLE`
     * @return [List] of all [WorkoutWithSetsAndExercise]
     */
    @Query("SELECT * FROM $WORKOUT_TABLE")
    fun getAllWorkoutWithSetsAndExercise(): Flow<List<WorkoutWithSetsAndExercise>>

    /**
     * Fetches all [WorkoutWithSetsAndExercise] from `WORKOUT_TABLE`
     * @return [PagingSource]<[Int],[WorkoutWithSetsAndExercise]>
     */
    @Query("SELECT * FROM $WORKOUT_TABLE")
    fun getAllWorkoutWithSetsAndExercisePaged(): Flow<PagingSource<Int, WorkoutWithSetsAndExercise>>
}
