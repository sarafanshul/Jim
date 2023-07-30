package com.projectdelta.jim.ui.home.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.projectdelta.jim.ui.destinations.CalendarPreviewScreenDestination
import com.projectdelta.jim.ui.destinations.WorkoutInfoScreenDestination
import com.projectdelta.jim.ui.home.HomeScreenViewModel
import com.projectdelta.jim.ui.home.MainTopAppBar
import com.projectdelta.jim.ui.home.states.UIState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * Home screen composable , with a tob bar and host screen [WorkoutSessionScreen]
 */
@Destination(start = true)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    when (val uiState = viewModel.uiState.value) {
        is UIState.LaunchCalendarScreen -> {
            navigator.navigate(
                CalendarPreviewScreenDestination(day = uiState.date, copy = uiState.copy)
            )
            viewModel.resetUIState()
        }

        is UIState.Default -> {

        }

        is UIState.Error -> {

        }

        is UIState.LaunchWorkoutInfoScreen -> {
            navigator.navigate(
                WorkoutInfoScreenDestination(
                    workout = uiState.workout
                )
            )
            viewModel.resetUIState()
        }
    }

    Scaffold(
        topBar = {
            MainTopAppBar(viewModel)
        },
        content = {
            WorkoutSessionScreen(
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )
        }
    )
}

