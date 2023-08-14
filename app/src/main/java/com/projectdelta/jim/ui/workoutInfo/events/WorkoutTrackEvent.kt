package com.projectdelta.jim.ui.workoutInfo.events

import com.projectdelta.jim.data.model.entity.WorkoutSet

sealed class WorkoutTrackEvent {

    data class UpdateWeight(val newWeight: Double) : WorkoutTrackEvent()

    data class UpdateRep(val newRep: Int) : WorkoutTrackEvent()

    data class OnWorkoutSetClick( val workoutSet: WorkoutSet ) : WorkoutTrackEvent()

    object IncrementWeight : WorkoutTrackEvent()

    object DecrementWeight : WorkoutTrackEvent()

    object IncrementRep : WorkoutTrackEvent()

    object DecrementRep : WorkoutTrackEvent()

    object SaveWorkoutSet : WorkoutTrackEvent()

    object ClearWorkoutSet : WorkoutTrackEvent()

    object DeleteWorkoutSet : WorkoutTrackEvent()
}
