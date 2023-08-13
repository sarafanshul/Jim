package com.projectdelta.jim.ui.workoutInfo.states

import androidx.compose.ui.graphics.Color
import com.projectdelta.jim.data.model.entity.WorkoutSet

/**
 * State of UI for workout Track Component/Screen
 * This majorly has 2 states
 * 1. when for new [WorkoutSet] addition, the state is [CreateNew]
 * 2. when editing existing [WorkoutSet], the state is [EditExisting]
 *
 */
sealed class WorkoutTrackUIState {

    data class CreateNew(
        val button1State: WorkoutTrackButtonState = WorkoutTrackButtonState.Save,
        val button2State: WorkoutTrackButtonState = WorkoutTrackButtonState.Clear,
    ) : WorkoutTrackUIState()

    data class EditExisting(
        val set: WorkoutSet,
        val button1State: WorkoutTrackButtonState = WorkoutTrackButtonState.Edit,
        val button2State: WorkoutTrackButtonState = WorkoutTrackButtonState.Delete,
        val setSelectedColor: Color = Color(0x80A5D6A7)
    ) : WorkoutTrackUIState()

}

/**
 * Button UI State to be used for displaying workout track buttons. as the name suggests
 */
sealed class WorkoutTrackButtonState(
    val text: String,
    val color: Color,
) {
    object Save : WorkoutTrackButtonState("SAVE", Color(0xFF2196F3)) // blue

    object Clear : WorkoutTrackButtonState("CLEAR", Color(0xFFEF5350)) // something red

    object Edit : WorkoutTrackButtonState("EDIT", Color(0xFF26A69A)) // green

    object Delete : WorkoutTrackButtonState("DELETE", Color(0xFFEF5350)) // something red

    object Loading : WorkoutTrackButtonState("Loading ...", Color(0xFFF9A825)) // or-ange
}
