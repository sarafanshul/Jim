package com.projectdelta.jim.ui.workout

import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.projectdelta.jim.data.model.relation.WWSEParameterProvider
import com.projectdelta.jim.data.model.relation.WorkoutWithSetsAndExercise
import com.projectdelta.jim.ui.theme.JimTheme
import com.projectdelta.jim.ui.theme.md_theme_dark_onBackground
import com.projectdelta.jim.util.callbackWParam

@Composable
fun WorkoutTrackComponent(
    workout: WorkoutWithSetsAndExercise,
    modifier: Modifier = Modifier,
    onWorkoutUpdate: callbackWParam<WorkoutWithSetsAndExercise>? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

    }
}


@Preview(showBackground = true)
@Composable
fun WorkoutTrackScreenPreview(
    @PreviewParameter(WWSEParameterProvider::class)
    workout: WorkoutWithSetsAndExercise
) {
    JimTheme {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            WorkoutTrackComponent(workout = workout)
        }
    }
}
