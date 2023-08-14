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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.projectdelta.jim.data.model.entity.WorkoutSet
import com.projectdelta.jim.data.model.relation.WWSEParameterProvider
import com.projectdelta.jim.data.model.relation.WorkoutWithSetsAndExercise
import com.projectdelta.jim.data.state.SessionState
import com.projectdelta.jim.ui.common.component.HorizontalNumberPicker
import com.projectdelta.jim.ui.common.component.SetLogComponent
import com.projectdelta.jim.ui.common.conditional
import com.projectdelta.jim.ui.common.widget.NotFoundFiller
import com.projectdelta.jim.ui.theme.JimTheme
import com.projectdelta.jim.ui.workoutInfo.states.WorkoutTrackUIState
import com.projectdelta.jim.util.Constants.UI.PADDING_NORMAL
import com.projectdelta.jim.util.Constants.UI.PADDING_SMALL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun WorkoutTrackComponent(
    workout: StateFlow<SessionState<WorkoutWithSetsAndExercise>>,
    uiState: StateFlow<WorkoutTrackUIState>,
    modifier: Modifier = Modifier,
) {
    val state by uiState.collectAsState()
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

        val defaultWeight = remember(state.set.weight.toInt()) {
            mutableStateOf(state.set.weight.toInt())
        }

        val defaultRep = remember(state.set.reps) {
            mutableStateOf(state.set.reps)
        }

        HorizontalNumberPicker(
            currentValue = defaultWeight,
            onValueIncrement = state.onValueIncrementWt,
            onValueDecrement = state.onValueDecrementWt,
            onValueChange = state.onValueChangeWt,
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
            currentValue = defaultRep,
            onValueIncrement = state.onValueIncrementReps,
            onValueDecrement = state.onValueDecrementReps,
            onValueChange = state.onValueChangeReps,
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
                onClick = state.button1State.onClick,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = PADDING_NORMAL),
                colors = ButtonDefaults.buttonColors(
                    containerColor = state.button1State.color
                ),
            ) {
                Text(text = state.button1State.text)
            }

            TextButton(
                onClick = state.button2State.onClick,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = PADDING_NORMAL),
                colors = ButtonDefaults.buttonColors(
                    containerColor = state.button2State.color
                ),
            ) {
                Text(text = state.button2State.text)
            }
        }

        val workoutState by workout.collectAsState()
        when (val temp = workoutState) {
            SessionState.Empty -> {
                NotFoundFiller(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth(),
                )
            }

            is SessionState.Session -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(temp.session.sets) {
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
                                .clickable { state.onWorkoutSetClick(it) }
                                .padding(vertical = PADDING_NORMAL) // padding
                        )
                        if (it != temp.session.sets.last()) {
                            Divider(
                                thickness = 1.dp,
                                modifier = Modifier.padding(horizontal = PADDING_NORMAL)
                            )
                        }
                    }
                }
            }
        }

    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun WorkoutTrackScreenCreateNewPreview(
    @PreviewParameter(WWSEParameterProvider::class)
    workout0: WorkoutWithSetsAndExercise
) {
    JimTheme {

        val state = remember {
            MutableStateFlow(
                WorkoutTrackUIState.CreateNew(WorkoutSet())
            ).asStateFlow()
        }

        val workout = remember {
            MutableStateFlow<SessionState<WorkoutWithSetsAndExercise>>(
                SessionState.Session(workout0)
            )
        }

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

@Preview(name = "Selected", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WorkoutTrackScreenEditSelectedPreview(
    @PreviewParameter(WWSEParameterProvider::class)
    workout0: WorkoutWithSetsAndExercise
) {
    JimTheme {

        val state = remember {
            MutableStateFlow(
                WorkoutTrackUIState.EditExisting(
                    workoutSet = workout0.sets[2]
                )
            ).asStateFlow()
        }

        val workout = remember {
            MutableStateFlow<SessionState<WorkoutWithSetsAndExercise>>(
                SessionState.Session(workout0)
            )
        }

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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun WorkoutTrackScreenSessionEmptyPreview(
    @PreviewParameter(WWSEParameterProvider::class)
    workout0: WorkoutWithSetsAndExercise
) {
    JimTheme {

        val state = remember {
            MutableStateFlow(
                WorkoutTrackUIState.CreateNew(WorkoutSet())
            ).asStateFlow()
        }

        val workout = remember {
            MutableStateFlow<SessionState<WorkoutWithSetsAndExercise>>(
                SessionState.Empty
            )
        }

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
