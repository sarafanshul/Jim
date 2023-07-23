package com.projectdelta.jim.data.state

import com.projectdelta.jim.data.model.WorkoutSession

/**
 * Manages session state for current day's workout session
 */
sealed class WorkoutSessionState{

    object NoSession : WorkoutSessionState()

    data class Session( val session: List<WorkoutSession> ) : WorkoutSessionState()

}
