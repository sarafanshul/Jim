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
    fun getByIdRanged(start: BaseId, end: BaseId): Flow<List<WorkoutSession>>

    /**
     * Fetches [WorkoutSession] and it's Workouts in form of [SessionWithWorkouts]
     */
    fun getSessionWithWorkouts(id: BaseId): Flow<List<SessionWithWorkouts>>

    /**
     * Fetches all [WorkoutSession] from database Joined [SessionWithWorkouts], ORDER_BY ASC
     * @return [List] of all [SessionWithWorkouts]
     */
    fun getAllSessionsWithWorkouts(): Flow<List<SessionWithWorkouts>>

    /**
     * Fetches all [WorkoutSession] from database Joined [SessionWithWorkouts], ORDER_BY ASC
     * @return [PagingData]<[SessionWithWorkouts]>
     */
    fun getAllSessionsWithWorkoutsPaged(): Flow<PagingData<SessionWithWorkouts>>

    /**
     * Fetches [WorkoutSession] and it's Workouts in form of Relation: [SessionWithWorkoutWithSets]
     */
    fun getSessionWithWorkoutsWithSets(id: BaseId): Flow<List<SessionWithWorkoutWithSets>>

    /**
     * Fetches all [WorkoutSession] from database Joined [SessionWithWorkoutWithSets], ORDER_BY ASC
     * @return [List] of all [SessionWithWorkoutWithSets]
     */
    fun getAllSessionWithWorkoutsWithSets(): Flow<List<SessionWithWorkoutWithSets>>

    /**
     * Fetches all [WorkoutSession] from database Joined [SessionWithWorkoutWithSets], ORDER_BY ASC
     * @return [PagingData]<[SessionWithWorkoutWithSets]>
     */
    fun getAllSessionWithWorkoutsWithSetsPaged(): Flow<PagingData<SessionWithWorkoutWithSets>>

    /**
     * Fetches [WorkoutSession] as [SessionState] JOIN object
     */
    fun getSessionByDay(day: Int): Flow<SessionState<WorkoutSession>>

    /**
     * Fetches [WorkoutSession] as [SessionWithWorkouts] JOIN object
     */
    fun getSessionWithWorkoutsByDay(day: Int): Flow<SessionState<SessionWithWorkouts>>

    /**
     * Fetches [WorkoutSession] as [SessionWithWorkoutWithSets] JOIN object
     */
    fun getSessionSessionWithWorkoutWithSetsByDay(day: Int): Flow<SessionState<SessionWithWorkoutWithSets>>

}
