package com.projectdelta.jim.ui.home.events

import com.projectdelta.jim.data.model.Workout

sealed class HomeScreenEvent {

    data class DateChangeEvent(val newDate: Int, val forceScroll: Boolean = false) :
        HomeScreenEvent()

    object CreateNewWorkoutEvent : HomeScreenEvent()

    data class LaunchCalendarEvent(val copy: Boolean = false) : HomeScreenEvent()

    data class WorkoutSelectedEvent(val workout: Workout) : HomeScreenEvent()

    object NavigationAppIconClickEvent : HomeScreenEvent()

    object LaunchSettingScreenEvent : HomeScreenEvent()

    data class ShareWorkoutEvent(val day: Int) : HomeScreenEvent()

    object LaunchBodyTrackScreenEvent : HomeScreenEvent()

    object LaunchAnalysisScreenEvent : HomeScreenEvent()

}
