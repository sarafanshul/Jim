package com.projectdelta.jim.ui.home.events

import com.projectdelta.jim.data.model.relation.WorkoutWithSetsAndExercise

sealed class HomeScreenEvent {

    data class DateChangeEvent(val newDate: Int) : HomeScreenEvent()

    data class PageChangeEvent(val newDate: Int) : HomeScreenEvent()

    data object NextDayClickEvent : HomeScreenEvent()

    data object PreviousDayClickEvent : HomeScreenEvent()

    data object CreateNewWorkoutEvent : HomeScreenEvent()

    data class LaunchCalendarEvent(val copy: Boolean = false) : HomeScreenEvent()

    data class WorkoutSelectedEvent(val workoutWithSetsAndExercise: WorkoutWithSetsAndExercise) : HomeScreenEvent()

    data object NavigationAppIconClickEvent : HomeScreenEvent()

    data object LaunchSettingScreenEvent : HomeScreenEvent()

    data class ShareWorkoutEvent(val day: Int) : HomeScreenEvent()

    data object LaunchBodyTrackScreenEvent : HomeScreenEvent()

    object LaunchAnalysisScreenEvent : HomeScreenEvent()

}
