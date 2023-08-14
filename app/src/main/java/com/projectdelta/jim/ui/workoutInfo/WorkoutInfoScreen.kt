package com.projectdelta.jim.ui.workoutInfo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.projectdelta.jim.util.BaseId
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun WorkoutInfoScreen(
    navigator: DestinationsNavigator,
    workout: BaseId,
    viewModel: WorkoutInfoViewModel = hiltViewModel()
) {
    LaunchedEffect(workout){
        viewModel.loadWorkout(workout)
    }

    Scaffold(
        topBar = {
            WorkoutInfoTopAppBar()
        },
        content = {
            WorkoutTrackComponent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                uiState = viewModel.workoutTrackUIState,
                workout = viewModel.workout
            )
        }
    )
}
