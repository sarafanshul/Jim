package com.projectdelta.jim.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.projectdelta.jim.data.model.WorkoutSession
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.util.Constants.Table.WORKOUT_SESSION_TABLE
import kotlinx.coroutines.flow.Flow

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
     * Fetches [WorkoutSession] by time (ms)
     * @param timeMs time of item to fetch
     * @return [List]<[WorkoutSession]> if found.
     */
    @Query("SELECT * FROM $WORKOUT_SESSION_TABLE WHERE timeMs = :timeMs")
    fun getByTime(timeMs: Long): Flow<List<WorkoutSession>>

    /**
     * Fetches [WorkoutSession] by time in range [[startTimeMs], [endTimeMs]]
     * @param startTimeMs (included)
     * @param endTimeMs (also included)
     * @return [List]<[WorkoutSession]> if found.
     */
    @Query("SELECT * FROM $WORKOUT_SESSION_TABLE WHERE timeMs BETWEEN :startTimeMs AND :endTimeMs")
    fun getByTimeRange(startTimeMs: Long, endTimeMs: Long): Flow<List<WorkoutSession>>

    /**
     * Fetches all [WorkoutSession] from database
     * @return [List] of all [WorkoutSession]
     */
    @Query("SELECT * FROM $WORKOUT_SESSION_TABLE ORDER BY timeMs ASC")
    fun getAllSessions(): Flow<List<WorkoutSession>>

    /**
     * Fetches all [WorkoutSession] in database, Paged, ORDER_BY ASC
     * @return [PagingSource]<[Int],[WorkoutSession]>
     */
    @Query("SELECT * FROM $WORKOUT_SESSION_TABLE ORDER BY timeMs ASC")
    fun getAllSessionsPaged(): PagingSource<Int, WorkoutSession>

}
