package com.projectdelta.jim.ui.home.states

import com.projectdelta.jim.data.model.Workout

sealed class UIState {

    object Default : UIState()

    object Error : UIState()

    data class LaunchCalendarScreen(val date: Int, val copy: Boolean) : UIState()

    data class LaunchWorkoutInfoScreen(val workout: Workout?) : UIState()

}
