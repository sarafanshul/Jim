package com.projectdelta.jim.ui.workoutInfo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.projectdelta.jim.util.BaseId
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun WorkoutInfoScreen(
    navigator: DestinationsNavigator,
    workout: BaseId,
) {
    Scaffold(
        topBar = {
            WorkoutInfoTopAppBar()
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )
        }
    )
}
