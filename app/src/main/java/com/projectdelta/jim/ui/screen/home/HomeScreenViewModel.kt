package com.projectdelta.jim.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectdelta.jim.data.repository.ExerciseRepository
import com.projectdelta.jim.data.repository.WorkoutSessionRepository
import com.projectdelta.jim.data.state.WorkoutSessionState
import com.projectdelta.jim.di.qualifiers.IODispatcher
import com.projectdelta.jim.util.TimeUtil.getCurrentDayFromEpoch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
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

    private val _workoutSessionState = MutableStateFlow<WorkoutSessionState>(
        WorkoutSessionState.NoSession
    )
    val workoutSessionState = _workoutSessionState.asStateFlow()

    init {
        registerObserver()
    }

    fun registerObserver() {
        viewModelScope.launch(worker) {
            homeScreenState.collectLatest { state ->
                workoutRepository.getSessionByDay(state.currentDay).collectLatest {
                    _workoutSessionState.value = it
                }
            }
        }
    }

    fun getWorkoutByDay(day : Int) : StateFlow<WorkoutSessionState> {
        viewModelScope.launch(worker){
            workoutRepository.getSessionByDay(day).collectLatest { state ->
                _workoutSessionState.update { state }
            }
        }
        return workoutSessionState
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
                    it.copy(currentDay = event.newDate)
                }
            }

            is HomeScreenEvent.WorkoutSelectedEvent -> {
                // TODO (IMPL)
            }
        }
    }
}
