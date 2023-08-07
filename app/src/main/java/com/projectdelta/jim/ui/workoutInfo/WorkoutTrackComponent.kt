package com.projectdelta.jim.ui.workoutInfo

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.projectdelta.jim.data.model.relation.WWSEParameterProvider
import com.projectdelta.jim.data.model.relation.WorkoutWithSetsAndExercise
import com.projectdelta.jim.ui.common.component.HorizontalNumberPicker
import com.projectdelta.jim.ui.common.component.SetLogComponent
import com.projectdelta.jim.ui.theme.JimTheme
import com.projectdelta.jim.util.Constants.UI.PADDING_NORMAL
import com.projectdelta.jim.util.Constants.UI.PADDING_SMALL

@Composable
fun WorkoutTrackComponent(
    workout: WorkoutWithSetsAndExercise,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = "WEIGHT",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(PADDING_SMALL)
        )
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
        HorizontalNumberPicker(
            currentValue = 0,
            onValueIncrement = { /*TODO*/ },
            onValueDecrement = { /*TODO*/ },
            onValueChangeListener = {},
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(PADDING_NORMAL)
        )
        Text(
            text = "REPS",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(PADDING_SMALL)
        )
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
        HorizontalNumberPicker(
            currentValue = 0,
            onValueIncrement = { /*TODO*/ },
            onValueDecrement = { /*TODO*/ },
            onValueChangeListener = {},
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(PADDING_NORMAL)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(PADDING_NORMAL)
        ) {
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = PADDING_NORMAL),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
            ) {
                Text(text = "SAVE")
            }
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = PADDING_NORMAL),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                ),
            ) {
                Text(text = "CLEAR")
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(workout.sets) {
                SetLogComponent(
                    set = it,
                    index = it.id,
                    onNotesClick = {},
                    modifier = Modifier.padding(PADDING_NORMAL)
                )
                Divider(
                    thickness = 1.dp,
                    modifier = Modifier.padding(PADDING_NORMAL)
                )
            }
        }

    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
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
