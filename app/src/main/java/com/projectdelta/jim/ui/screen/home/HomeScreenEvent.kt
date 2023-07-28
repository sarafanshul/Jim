package com.projectdelta.jim.ui.screen.home

import com.projectdelta.jim.data.model.Workout

sealed class HomeScreenEvent{

    data class DateChangeEvent( val newDate : Int, val forceScroll: Boolean = false ) : HomeScreenEvent()

    object CreateNewWorkoutEvent : HomeScreenEvent()

    object CopyPreviousWorkoutEvent : HomeScreenEvent()

    data class WorkoutSelectedEvent( val workout: Workout ) : HomeScreenEvent()

}
