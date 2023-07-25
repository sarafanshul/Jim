package com.projectdelta.jim.ui.events

import com.projectdelta.jim.data.model.Workout
import com.projectdelta.jim.data.state.WorkoutSessionState
import kotlinx.coroutines.flow.Flow

/**
 * Workout Session Event handle
 */
interface WorkoutSessionScreenEventsHandler{

    fun loadSessionForDay( day : Int ) : Flow<WorkoutSessionState>

    fun addNewWorkout()

    fun copyPreviousWorkout()

    fun moveDayPrevious()

    fun moveDayForward()

    fun onWorkoutSelected( workout: Workout)

}
