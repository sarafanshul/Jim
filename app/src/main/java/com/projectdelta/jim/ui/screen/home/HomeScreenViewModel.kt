package com.projectdelta.jim.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectdelta.jim.data.repository.ExerciseRepository
import com.projectdelta.jim.data.repository.WorkoutSessionRepository
import com.projectdelta.jim.di.qualifiers.IODispatcher
import com.projectdelta.jim.util.TimeUtil.getCurrentDayFromEpoch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val workoutRepository: WorkoutSessionRepository,
    @IODispatcher private val worker: CoroutineDispatcher,
) : ViewModel() {

    private val _homeScreenState = MutableStateFlow(
        HomeScreenState(getCurrentDayFromEpoch())
    )
    val homeScreenState = _homeScreenState.asStateFlow()

    fun getWorkoutByDay(day: Int) = workoutRepository.getSessionByDay(day)

    /**
     * Updates the [HomeScreenState.currentDay] silently without notifying the observers.
     * This works (till now) because the object ref of the [MutableStateFlow] remains the same
     * and only value is updated.
     * **May god this doesn't break**.
     */
    fun updateCurrentDaySilently(day: Int) {
        _homeScreenState.value.currentDay = day
    }

    fun handleEvent(event: HomeScreenEvent) = viewModelScope.launch(worker) {
        when (event) {

            is HomeScreenEvent.CreateNewWorkoutEvent -> {
            }

            is HomeScreenEvent.CopyPreviousWorkoutEvent -> {
                // TODO (IMPL)
            }

            is HomeScreenEvent.DateChangeEvent -> {
                _homeScreenState.update {
                    Timber.d("Event called with date: ${event.newDate}, current:${it.currentDay}")
                    it.copy(currentDay = event.newDate)
                }
            }

            is HomeScreenEvent.WorkoutSelectedEvent -> {
                // TODO (IMPL)
            }
        }
    }
}
