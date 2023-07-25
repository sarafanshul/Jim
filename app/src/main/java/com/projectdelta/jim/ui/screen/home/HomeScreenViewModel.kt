package com.projectdelta.jim.ui.screen.home

import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectdelta.jim.data.model.Workout
import com.projectdelta.jim.data.repository.ExerciseRepository
import com.projectdelta.jim.data.repository.WorkoutSessionRepository
import com.projectdelta.jim.data.state.WorkoutSessionState
import com.projectdelta.jim.di.qualifiers.IODispatcher
import com.projectdelta.jim.ui.events.WorkoutSessionScreenEventsHandler
import com.projectdelta.jim.util.TimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val workoutRepository: WorkoutSessionRepository,
    @IODispatcher private val worker: CoroutineDispatcher,
): ViewModel(), WorkoutSessionScreenEventsHandler {

    val pagerState = PagerState(
        initialPage = TimeUtil
            .millisecondsToDays(System.currentTimeMillis())
    )

    override fun loadSessionForDay(day: Int): Flow<WorkoutSessionState> {
        return workoutRepository.getSessionByDay(day)
    }

    override fun addNewWorkout() {
        // TODO("Not yet implemented")
    }

    override fun copyPreviousWorkout() {
        // TODO("Not yet implemented")
    }

    override fun moveDayPrevious() {
        viewModelScope.launch(worker) {
            pagerState.animateScrollToPage(
                pagerState.currentPage - 1
            )
        }
    }

    override fun moveDayForward() {
        viewModelScope.launch(worker) {
            pagerState.animateScrollToPage(
                pagerState.currentPage + 1
            )
        }
    }

    override fun onWorkoutSelected(workout: Workout) {
        // TODO("Not yet implemented")
    }

}
