package com.projectdelta.jim.data.repository

import androidx.paging.PagingData
import com.projectdelta.jim.data.model.entity.WorkoutSession
import com.projectdelta.jim.data.model.relation.SessionWithWorkoutWithSets
import com.projectdelta.jim.data.model.relation.SessionWithWorkouts
import com.projectdelta.jim.data.state.SessionState
import com.projectdelta.jim.util.BaseId
import kotlinx.coroutines.flow.Flow

interface WorkoutSessionRepository : BaseDBRepository<WorkoutSession> {

    /**
     * Fetches [WorkoutSession] by time in range [[start], [end]]
     * @param start (included)
     * @param end (also included)
     * @return [List]<[WorkoutSession]> if found.
     */
    suspend fun getByIdRanged(start: BaseId, end: BaseId): Flow<List<WorkoutSession>>

    /**
     * Fetches [WorkoutSession] and it's Workouts in form of [SessionWithWorkouts]
     */
    suspend fun getSessionWithWorkoutsById(id: BaseId): Flow<List<SessionWithWorkouts>>

    /**
     * Fetches all [WorkoutSession] from database Joined [SessionWithWorkouts], ORDER_BY ASC
     * @return [List] of all [SessionWithWorkouts]
     */
    suspend fun getAllSessionsWithWorkouts(): Flow<List<SessionWithWorkouts>>

    /**
     * Fetches all [WorkoutSession] from database Joined [SessionWithWorkouts], ORDER_BY ASC
     * @return [PagingData]<[SessionWithWorkouts]>
     */
    suspend fun getAllSessionsWithWorkoutsPaged(): Flow<PagingData<SessionWithWorkouts>>

    /**
     * Fetches [WorkoutSession] and it's Workouts in form of Relation: [SessionWithWorkoutWithSets]
     */
    suspend fun getSessionWithWorkoutsWithSets(id: BaseId): Flow<List<SessionWithWorkoutWithSets>>

    /**
     * Fetches all [WorkoutSession] from database Joined [SessionWithWorkoutWithSets], ORDER_BY ASC
     * @return [List] of all [SessionWithWorkoutWithSets]
     */
    suspend fun getAllSessionWithWorkoutsWithSets(): Flow<List<SessionWithWorkoutWithSets>>

    /**
     * Fetches all [WorkoutSession] from database Joined [SessionWithWorkoutWithSets], ORDER_BY ASC
     * @return [PagingData]<[SessionWithWorkoutWithSets]>
     */
    suspend fun getAllSessionWithWorkoutsWithSetsPaged(): Flow<PagingData<SessionWithWorkoutWithSets>>

    /**
     * Fetches [WorkoutSession] as [SessionState] JOIN object
     */
    suspend fun getSessionByDay(day: Int): Flow<SessionState<WorkoutSession>>

    /**
     * Fetches [WorkoutSession] as [SessionWithWorkouts] JOIN object
     */
    suspend fun getSessionWithWorkoutsByDay(day: Int): Flow<SessionState<SessionWithWorkouts>>

    /**
     * Fetches [WorkoutSession] as [SessionWithWorkoutWithSets] JOIN object
     */
    suspend fun getSessionWithWorkoutWithSetsByDay(day: Int): Flow<SessionState<SessionWithWorkoutWithSets>>

    /**
     * Fetches [WorkoutSession] as [SessionWithWorkoutWithSets] JOIN object in a inclusive [ [startDay] , [endDay] ] range
     *
     * note : make this paged ?
     */
    suspend fun getSessionWithWorkoutWithSetsByDayRanged(startDay: Int, endDay: Int): Flow<List<SessionState<SessionWithWorkoutWithSets>>>

}
