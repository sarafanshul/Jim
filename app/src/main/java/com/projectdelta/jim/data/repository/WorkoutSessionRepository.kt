package com.projectdelta.jim.data.repository

import com.projectdelta.jim.data.model.WorkoutSession
import com.projectdelta.jim.data.state.WorkoutSessionState
import com.projectdelta.jim.util.BaseId
import kotlinx.coroutines.flow.Flow

interface WorkoutSessionRepository {

    /**
     * Fetches [WorkoutSession] by id
     * @param id unique id of item to fetch
     * @return [WorkoutSession] if found.
     */
    fun getById(id : BaseId) : Flow<WorkoutSession?>

    /**
     * Fetches [WorkoutSession] by time (ms)
     * @param timeMs time of item to fetch
     * @return [List]<[WorkoutSession]> if found.
     */
    fun getByTime(timeMs : Long) : Flow<List<WorkoutSession>>

    fun getSessionByDay(day : Int) : Flow<WorkoutSessionState>

}
