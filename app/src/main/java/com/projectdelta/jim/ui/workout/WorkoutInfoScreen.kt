package com.projectdelta.jim.ui.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.projectdelta.jim.util.BaseId
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun WorkoutInfoScreen(
    navigator: DestinationsNavigator,
    workout: BaseId,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red),
    )
}
