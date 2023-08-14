package com.projectdelta.jim.ui.workoutInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectdelta.jim.data.model.entity.WorkoutSet
import com.projectdelta.jim.data.model.relation.WorkoutWithSetsAndExercise
import com.projectdelta.jim.data.repository.WorkoutRepository
import com.projectdelta.jim.data.repository.WorkoutSetRepository
import com.projectdelta.jim.data.state.SessionState
import com.projectdelta.jim.di.qualifiers.IODispatcher
import com.projectdelta.jim.ui.workoutInfo.events.WorkoutTrackEvent
import com.projectdelta.jim.ui.workoutInfo.states.WorkoutTrackUIState
import com.projectdelta.jim.util.BaseId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * WIP , do not copy, LOL
 */
@HiltViewModel
class WorkoutInfoViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val workoutSetRepository: WorkoutSetRepository,
    @IODispatcher private val worker: CoroutineDispatcher,
) : ViewModel() {

    init {
        observe()
    }

    private fun observe() = viewModelScope.launch(worker) {
        _workout.collectLatest {
            if (it is SessionState.Session)
                onWorkoutLoaded(it.session)
        }
    }

    private val _workout = MutableStateFlow<SessionState<WorkoutWithSetsAndExercise>>(
        SessionState.Empty
    )

    val workout: StateFlow<SessionState<WorkoutWithSetsAndExercise>>
        get() = _workout

    private val _workoutTrackUIState = MutableStateFlow<WorkoutTrackUIState>(
        WorkoutTrackUIState.Loading
    )

    val workoutTrackUIState: StateFlow<WorkoutTrackUIState>
        get() = _workoutTrackUIState

    fun loadWorkout(id: BaseId) = viewModelScope.launch(worker) {
        workoutRepository.getWorkoutWithSetsAndExerciseById(id).collectLatest {
            _workout.value = it
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun handleWorkoutTrackEvent(event: WorkoutTrackEvent) = viewModelScope.launch(worker) {
        when (event) {
            WorkoutTrackEvent.ClearWorkoutSet -> {
                _workoutTrackUIState.value.set.reps = 0
                _workoutTrackUIState.value.set.weight = 0.0
            }

            WorkoutTrackEvent.DecrementRep -> {
                _workoutTrackUIState.update {
                    val rep = it.set.reps
                    it.set.reps = maxOf(rep - 1, 0)
                    it
                }
            }

            WorkoutTrackEvent.DecrementWeight -> {
                val wt = _workoutTrackUIState.value.set.weight
                _workoutTrackUIState.value.set.weight = maxOf(wt - 1, 0.0)
            }

            WorkoutTrackEvent.IncrementRep -> {
                val rep = _workoutTrackUIState.value.set.reps
                _workoutTrackUIState.value.set.reps = maxOf(rep + 1, 200)
            }

            WorkoutTrackEvent.IncrementWeight -> {
                _workoutTrackUIState.value.set.weight++
            }

            is WorkoutTrackEvent.OnWorkoutSetClick -> {
                if( _workoutTrackUIState.value.set == event.workoutSet ){
                    // remove selection

                }else{
                    updateUIState(event.workoutSet) // update selected to new
                }
            }

            WorkoutTrackEvent.SaveWorkoutSet -> {
                if (_workoutTrackUIState.value.set.weight != 0.0 &&
                    _workoutTrackUIState.value.set.reps != 0
                ) {
                    workoutSetRepository.insert(_workoutTrackUIState.value.set)
                    // at this moment we shift to create new.
                    // ui should refresh
                }
            }

            WorkoutTrackEvent.DeleteWorkoutSet -> {
                workoutSetRepository.delete(_workoutTrackUIState.value.set)
                // ui should refresh
            }

            is WorkoutTrackEvent.UpdateRep -> {
                _workoutTrackUIState.value.set.reps = event.newRep
            }

            is WorkoutTrackEvent.UpdateWeight -> {
                _workoutTrackUIState.value.set.weight = event.newWeight
            }
        }
    }

    // todo refactor this to use ::handleWorkoutTrackEvent instead. (needed ASAP)
    private fun updateUIState(workoutSet: WorkoutSet, /* add state type to update */) {
        _workoutTrackUIState.update {
            WorkoutTrackUIState.EditExisting(
                workoutSet = workoutSet,
                incrementWt = {
                    handleWorkoutTrackEvent(WorkoutTrackEvent.IncrementWeight)
                },
                decrementWt = {
                    handleWorkoutTrackEvent(WorkoutTrackEvent.DecrementWeight)
                },
                changeWt = {
                    handleWorkoutTrackEvent(WorkoutTrackEvent.UpdateWeight(it.toDouble()))
                },
                incrementReps = {
                    handleWorkoutTrackEvent(WorkoutTrackEvent.IncrementRep)
                },
                decrementReps = {
                    handleWorkoutTrackEvent(WorkoutTrackEvent.DecrementRep)
                },
                changeReps = {
                    handleWorkoutTrackEvent(WorkoutTrackEvent.UpdateRep(it))
                },
                workoutSetClick = {
                    handleWorkoutTrackEvent(WorkoutTrackEvent.OnWorkoutSetClick(it))
                },
                onSave = {
                    handleWorkoutTrackEvent(WorkoutTrackEvent.SaveWorkoutSet)
                },
                onDelete = {
                    handleWorkoutTrackEvent(WorkoutTrackEvent.DeleteWorkoutSet)
                },
            )
        }
    }

    private fun onWorkoutLoaded(workout: WorkoutWithSetsAndExercise) {
        _workoutTrackUIState.update {
            getNewWorkoutUIState(workout)
        }
    }

    private fun getNewWorkoutUIState(workout: WorkoutWithSetsAndExercise) = WorkoutTrackUIState.CreateNew(
        workoutSet = WorkoutSet.buildNew(
            workoutId = workout.workout.id,
            exerciseId = workout.workout.exerciseId
        ),
        incrementWt = {
            handleWorkoutTrackEvent(WorkoutTrackEvent.IncrementWeight)
        },
        decrementWt = {
            handleWorkoutTrackEvent(WorkoutTrackEvent.DecrementWeight)
        },
        changeWt = {
            handleWorkoutTrackEvent(WorkoutTrackEvent.UpdateWeight(it.toDouble()))
        },
        incrementReps = {
            handleWorkoutTrackEvent(WorkoutTrackEvent.IncrementRep)
        },
        decrementReps = {
            handleWorkoutTrackEvent(WorkoutTrackEvent.DecrementRep)
        },
        changeReps = {
            handleWorkoutTrackEvent(WorkoutTrackEvent.UpdateRep(it))
        },
        workoutSetClick = {
            handleWorkoutTrackEvent(WorkoutTrackEvent.OnWorkoutSetClick(it))
        },
        onSave = {
            handleWorkoutTrackEvent(WorkoutTrackEvent.SaveWorkoutSet)
        },
        onClear = {
            handleWorkoutTrackEvent(WorkoutTrackEvent.ClearWorkoutSet)
        },
    )

}
