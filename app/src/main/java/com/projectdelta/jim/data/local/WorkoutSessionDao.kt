package com.projectdelta.jim.data.local

import androidx.room.Dao
import androidx.room.Query
import com.projectdelta.jim.data.model.WorkoutSession
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.util.Constants.Table.WORKOUT_SESSION_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSessionDao : BaseDao<WorkoutSession>{

    /**
     * Fetches [WorkoutSession] by id
     * @param id unique id of item to fetch
     * @return [WorkoutSession] if found.
     */
    @Query("SELECT * FROM $WORKOUT_SESSION_TABLE WHERE id = :id")
    fun getById(id : BaseId) : Flow<WorkoutSession?>

    /**
     * Fetches [WorkoutSession] by time (ms)
     * @param timeMs time of item to fetch
     * @return [List]<[WorkoutSession]> if found.
     */
    @Query("SELECT * FROM $WORKOUT_SESSION_TABLE WHERE timeMs = :timeMs")
    fun getByTime(timeMs : Long) : Flow<List<WorkoutSession>>

}
