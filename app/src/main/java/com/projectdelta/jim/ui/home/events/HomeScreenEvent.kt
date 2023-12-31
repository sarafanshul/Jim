package com.projectdelta.jim.ui.home.events

import com.projectdelta.jim.data.model.relation.WorkoutWithSetsAndExercise

sealed class HomeScreenEvent {

    data class DateChangeEvent(val newDate: Int, val forceScroll: Boolean = false) :
        HomeScreenEvent()

    object CreateNewWorkoutEvent : HomeScreenEvent()

    data class LaunchCalendarEvent(val copy: Boolean = false) : HomeScreenEvent()

    data class WorkoutSelectedEvent(val workoutWithSetsAndExercise: WorkoutWithSetsAndExercise) : HomeScreenEvent()

    object NavigationAppIconClickEvent : HomeScreenEvent()

    object LaunchSettingScreenEvent : HomeScreenEvent()

    data class ShareWorkoutEvent(val day: Int) : HomeScreenEvent()

    object LaunchBodyTrackScreenEvent : HomeScreenEvent()

    object LaunchAnalysisScreenEvent : HomeScreenEvent()

}
