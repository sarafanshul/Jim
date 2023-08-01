package com.projectdelta.jim.ui.home.states

import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.util.BaseId

sealed class UIState {

    object Default : UIState()

    object Error : UIState()

    data class LaunchCalendarScreen(val date: Int, val copy: Boolean) : UIState()

    data class LaunchWorkoutInfoScreen(val workoutId: BaseId) : UIState()

}
