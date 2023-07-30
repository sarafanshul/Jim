package com.projectdelta.jim.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.projectdelta.jim.data.model.entity.WorkoutSession
import com.projectdelta.jim.data.model.relation.SessionWithWorkoutWithSets
import com.projectdelta.jim.data.model.relation.SessionWithWorkouts
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.util.Constants.Table.WORKOUT_SESSION_TABLE
import kotlinx.coroutines.flow.Flow

/**
 * [Dao] for [WorkoutSession] Table.
 */
@Dao
interface WorkoutSessionDao : BaseDao<WorkoutSession> {

    /**
     * Fetches [WorkoutSession] by id
     * @param id unique id of item to fetch
     * @return [WorkoutSession] if found.
     */
    @Query("SELECT * FROM $WORKOUT_SESSION_TABLE WHERE id = :id")
    fun getById(id: BaseId): Flow<WorkoutSession?>

    /**
     * Fetches [WorkoutSession] by time in range [[start], [end]]
     * @param start (included)
     * @param end (also included)
     * @return [List]<[WorkoutSession]> if found.
     */
    @Query("SELECT * FROM $WORKOUT_SESSION_TABLE WHERE id BETWEEN :start AND :end")
    fun getByIdRanged(start: BaseId, end: BaseId): Flow<List<WorkoutSession>>

    /**
     * Fetches all [WorkoutSession] from database
     * @return [List] of all [WorkoutSession]
     */
    @Query("SELECT * FROM $WORKOUT_SESSION_TABLE ORDER BY id ASC")
    fun getAllSessions(): Flow<List<WorkoutSession>>

    /**
     * Fetches all [WorkoutSession] in database, Paged, ORDER_BY ASC
     * @return [PagingSource]<[Int],[WorkoutSession]>
     */
    @Query("SELECT * FROM $WORKOUT_SESSION_TABLE ORDER BY id ASC")
    fun getAllSessionsPaged(): PagingSource<Int, WorkoutSession>

    /**
     * Fetches [WorkoutSession] and it's Workouts in form of [SessionWithWorkouts]
     */
    @Query("SELECT * FROM $WORKOUT_SESSION_TABLE WHERE id = :id")
    fun getSessionWithWorkouts(id: BaseId): Flow<List<SessionWithWorkouts>>

    /**
     * Fetches all [WorkoutSession] from database Joined [SessionWithWorkouts], ORDER_BY ASC
     * @return [List] of all [SessionWithWorkouts]
     */
    @Query("SELECT * FROM $WORKOUT_SESSION_TABLE ORDER BY id ASC")
    fun getAllSessionsWithWorkouts(): Flow<List<SessionWithWorkouts>>

    /**
     * Fetches all [WorkoutSession] from database Joined [SessionWithWorkouts], ORDER_BY ASC
     * @return [PagingSource]<[Int],[SessionWithWorkouts]>
     */
    @Query("SELECT * FROM $WORKOUT_SESSION_TABLE ORDER BY id ASC")
    fun getAllSessionsWithWorkoutsPaged(): PagingSource<Int, SessionWithWorkouts>

    /**
     * Fetches [WorkoutSession] and it's Workouts in form of Relation: [SessionWithWorkoutWithSets]
     */
    @Query("SELECT * FROM $WORKOUT_SESSION_TABLE WHERE id = :id")
    fun getSessionWithWorkoutsWithSets(id: BaseId): Flow<List<SessionWithWorkoutWithSets>>

    /**
     * Fetches all [WorkoutSession] from database Joined [SessionWithWorkoutWithSets], ORDER_BY ASC
     * @return [List] of all [SessionWithWorkoutWithSets]
     */
    @Query("SELECT * FROM $WORKOUT_SESSION_TABLE ORDER BY id ASC")
    fun getAllSessionWithWorkoutsWithSets(): Flow<List<SessionWithWorkoutWithSets>>

    /**
     * Fetches all [WorkoutSession] from database Joined [SessionWithWorkoutWithSets], ORDER_BY ASC
     * @return [PagingSource]<[Int],[SessionWithWorkoutWithSets]>
     */
    @Query("SELECT * FROM $WORKOUT_SESSION_TABLE ORDER BY id ASC")
    fun getAllSessionWithWorkoutsWithSetsPaged(): PagingSource<Int, SessionWithWorkoutWithSets>

}
