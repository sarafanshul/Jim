package com.projectdelta.jim.ui.workoutInfo

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.projectdelta.jim.data.model.relation.WWSEParameterProvider
import com.projectdelta.jim.data.model.relation.WorkoutWithSetsAndExercise
import com.projectdelta.jim.ui.common.component.HorizontalNumberPicker
import com.projectdelta.jim.ui.common.component.SetLogComponent
import com.projectdelta.jim.ui.common.conditional
import com.projectdelta.jim.ui.theme.JimTheme
import com.projectdelta.jim.ui.workoutInfo.states.WorkoutTrackButtonState
import com.projectdelta.jim.ui.workoutInfo.states.WorkoutTrackUIState
import com.projectdelta.jim.util.Constants.UI.PADDING_NORMAL
import com.projectdelta.jim.util.Constants.UI.PADDING_SMALL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun WorkoutTrackComponent(
    workout: WorkoutWithSetsAndExercise,
    uiState: StateFlow<WorkoutTrackUIState>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = PADDING_NORMAL)
            .fillMaxWidth(),
    ) {
        Text(
            text = "WEIGHT (Kgs)",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(PADDING_SMALL)
        )
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )

        var defaultWeight = remember { 0 }
        var defaultReps = remember { 0 }
        var btn1state : WorkoutTrackButtonState = remember { WorkoutTrackButtonState.Loading }
        var btn2state : WorkoutTrackButtonState= remember { WorkoutTrackButtonState.Loading }
        val state by uiState.collectAsState()

        when(val temp = state){
            is WorkoutTrackUIState.CreateNew -> {
                defaultWeight = 0
                defaultReps = 0
                btn1state = temp.button1State
                btn2state = temp.button2State
            }
            is WorkoutTrackUIState.EditExisting -> {
                defaultWeight = temp.set.weight.toInt()
                defaultReps = temp.set.reps
                btn1state = temp.button1State
                btn2state = temp.button2State
            }
        }

        HorizontalNumberPicker(
            currentValue = defaultWeight,
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
            currentValue = defaultReps,
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
                .padding(vertical = PADDING_NORMAL)
        ) {
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = PADDING_NORMAL),
                colors = ButtonDefaults.buttonColors(
                    containerColor = btn1state.color
                ),
            ) {
                Text(text = btn1state.text)
            }
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = PADDING_NORMAL),
                colors = ButtonDefaults.buttonColors(
                    containerColor = btn2state.color
                ),
            ) {
                Text(text = btn2state.text)
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
                    modifier = Modifier
                        .conditional(
                            state is WorkoutTrackUIState.EditExisting &&
                                    (state as WorkoutTrackUIState.EditExisting).set == it
                        ) {
                            background((state as WorkoutTrackUIState.EditExisting).setSelectedColor)
                        }
                        .clickable {
                            // update [EditExisting] here.
                        }
                        .padding(vertical = PADDING_NORMAL) // padding
                )
                if( it != workout.sets.last() ) {
                    Divider(
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = PADDING_NORMAL)
                    )
                }
            }
        }

    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun WorkoutTrackScreenCreateNewPreview(
    @PreviewParameter(WWSEParameterProvider::class)
    workout: WorkoutWithSetsAndExercise
) {
    JimTheme {

        val state: StateFlow<WorkoutTrackUIState> = MutableStateFlow(
            WorkoutTrackUIState.CreateNew()
        ).asStateFlow()

        Surface {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                WorkoutTrackComponent(
                    workout = workout,
                    uiState = state
                )
            }
        }
    }
}

@Preview(name = "Selected",showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WorkoutTrackScreenEditSelectedPreview(
    @PreviewParameter(WWSEParameterProvider::class)
    workout: WorkoutWithSetsAndExercise
) {
    JimTheme {

        val state: StateFlow<WorkoutTrackUIState> = MutableStateFlow(
            WorkoutTrackUIState.EditExisting(
                set = workout.sets[2]
            )
        ).asStateFlow()

        Surface {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                WorkoutTrackComponent(
                    workout = workout,
                    uiState = state
                )
            }
        }
    }
}
