package com.projectdelta.jim.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectdelta.jim.data.model.relation.SessionWithWorkoutWithSets
import com.projectdelta.jim.data.repository.WorkoutSessionRepository
import com.projectdelta.jim.data.state.SessionState
import com.projectdelta.jim.di.qualifiers.IODispatcher
import com.projectdelta.jim.ui.home.events.HomeScreenEvent
import com.projectdelta.jim.ui.home.states.HomeScreenState
import com.projectdelta.jim.ui.home.states.UIState
import com.projectdelta.jim.util.TimeUtil.getCurrentDayFromEpoch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * TODO: Remove compose deps from Viewmodel, not a good practice.
 */
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val workoutRepository: WorkoutSessionRepository,
    @IODispatcher private val worker: CoroutineDispatcher,
) : ViewModel() {

    private val _homeScreenState = MutableStateFlow(
        HomeScreenState(getCurrentDayFromEpoch())
    )
    val homeScreenState = _homeScreenState.asStateFlow()

    private val _uiState = mutableStateOf<UIState>(UIState.Default)
    val uiState: State<UIState>
        get() = _uiState

    fun handleEvent(event: HomeScreenEvent) = viewModelScope.launch(worker) {
        when (event) {

            is HomeScreenEvent.CreateNewWorkoutEvent -> {
                Timber.d("Create new workout")
            }

            is HomeScreenEvent.DateChangeEvent -> {
                _homeScreenState.update {
                    Timber.d("Event called with date: ${event.newDate}, current:${it.currentDay}")
                    it.copy(currentDay = event.newDate)
                }
            }

            is HomeScreenEvent.WorkoutSelectedEvent -> {
                Timber.d("Explore Workout : ${event.workoutWithSetsAndExercise}")
                _uiState.value = UIState.LaunchWorkoutInfoScreen(event.workoutWithSetsAndExercise.workout.id)
            }

            is HomeScreenEvent.NavigationAppIconClickEvent -> {
                Timber.d("Nav App Icon Clicked")
            }

            is HomeScreenEvent.LaunchCalendarEvent -> {
                Timber.d("Launch calendar, copy : ${event.copy}")
                _uiState.value = UIState.LaunchCalendarScreen(-1, event.copy)
            }

            is HomeScreenEvent.LaunchSettingScreenEvent -> {
                Timber.d("Open Settings")
            }

            is HomeScreenEvent.ShareWorkoutEvent -> {
                Timber.d("Share called for day: ${event.day}")
            }

            is HomeScreenEvent.LaunchBodyTrackScreenEvent -> {
                Timber.d("Launch body Tracker")
            }

            is HomeScreenEvent.LaunchAnalysisScreenEvent -> {
                Timber.d("Launch analysis screen")
            }
        }
    }

    // FIXME: not sure this is best idea
    private val workoutCache: HashMap<Int,  Flow<SessionState<SessionWithWorkoutWithSets>>> = hashMapOf()

    fun getWorkoutByDay(day: Int) : Flow<SessionState<SessionWithWorkoutWithSets>> {
        if( !workoutCache.containsKey(day) ) {
            Timber.d("polling for day: $day")
            workoutCache[day] = workoutRepository.getSessionWithWorkoutWithSetsByDay(day)
        }
        return workoutCache[day]!!
    }

    /**
     * Updates the [HomeScreenState.currentDay] silently without notifying the observers.
     * This works (till now) because the object ref of the [MutableStateFlow] remains the same
     * and only value is updated.
     * **May god this doesn't break**.
     */
    fun updateCurrentDaySilently(day: Int) {
        _homeScreenState.value.currentDay = day
    }

    /**
     * Resets [UIState].
     */
    fun resetUIState() {
        _uiState.value = UIState.Default
    }
}
