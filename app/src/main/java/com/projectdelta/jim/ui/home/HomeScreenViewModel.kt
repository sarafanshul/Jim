package com.projectdelta.jim.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.projectdelta.jim.data.model.relation.SessionWithWorkoutWithSets
import com.projectdelta.jim.data.repository.WorkoutSessionRepository
import com.projectdelta.jim.data.state.SessionState
import com.projectdelta.jim.di.qualifiers.IODispatcher
import com.projectdelta.jim.ui.home.events.HomeScreenEvent
import com.projectdelta.jim.ui.home.states.HomeScreenState
import com.projectdelta.jim.ui.home.states.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private var currentDay: Int = 0

    private val _homeScreenState = MutableStateFlow(HomeScreenState(currentDay))
    val homeScreenState = _homeScreenState.asStateFlow()

    private val _uiState = mutableStateOf<UIState>(UIState.Default)
    val uiState: State<UIState>
        get() = _uiState

    suspend fun setCurrentDay(day: Int) {
        updateCurrentDayAndNotifyObservers(day)
    }

    fun handleEvent(event: HomeScreenEvent) = viewModelScope.launch(worker) {
        when (event) {

            is HomeScreenEvent.CreateNewWorkoutEvent -> {
                Timber.d("Create new workout")
            }

            is HomeScreenEvent.DateChangeEvent -> {
                updateCurrentDayAndNotifyObservers(event.newDate)
            }

            is HomeScreenEvent.PageChangeEvent -> {
                currentDay = event.newDate
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
                _uiState.value = UIState.LaunchCalendarScreen(currentDay, event.copy)
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

            is HomeScreenEvent.NextDayClickEvent -> {
                updateCurrentDayAndNotifyObservers(currentDay + 1)
            }

            is HomeScreenEvent.PreviousDayClickEvent -> {
                updateCurrentDayAndNotifyObservers(currentDay - 1)
            }
        }
    }

    private suspend fun updateCurrentDayAndNotifyObservers(day: Int) {
        if( day == currentDay ){
            return
        }
        withContext(worker) {
            currentDay = day
            _homeScreenState.update {
                Timber.d("Event called with date: ${day}, currentState:${it.currentDay}, current: $currentDay")
                it.copy(currentDay = day)
            }
        }
    }

    fun getWorkoutSessionPaged(): Flow<PagingData<SessionState<SessionWithWorkoutWithSets>>> {
        return workoutRepository
            .getAllSessionWithWorkoutsWithSetsPaged()
            .cachedIn(viewModelScope)
    }

    /**
     * Resets [UIState].
     */
    fun resetUIState() {
        _uiState.value = UIState.Default
    }
}
