package com.projectdelta.jim.ui.workoutInfo.states

import androidx.compose.ui.graphics.Color
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSet
import com.projectdelta.jim.ui.workoutInfo.events.WorkoutTrackEvent
import com.projectdelta.jim.util.callback
import com.projectdelta.jim.util.callbackWParam

/**
 * State of UI for workout Track Component/Screen
 * This majorly has 2 states
 * 1. when for new [WorkoutSet] addition, the state is [CreateNew]
 * 2. when editing existing [WorkoutSet], the state is [EditExisting]
 */
sealed class WorkoutTrackUIState(
    var set: WorkoutSet,
    val button1State: WorkoutTrackButtonState,
    val button2State: WorkoutTrackButtonState,
    val onValueIncrementWt: callback,
    val onValueDecrementWt: callback,
    val onValueChangeWt: callbackWParam<Int>,
    val onValueIncrementReps: callback,
    val onValueDecrementReps: callback,
    val onValueChangeReps: callbackWParam<Int>,
    val onWorkoutSetClick: callbackWParam<WorkoutSet>,
) {
    abstract fun unchangedCopy(): WorkoutTrackUIState

    object Loading : WorkoutTrackUIState(
        set = WorkoutSet(),
        button1State = WorkoutTrackButtonState.Loading,
        button2State = WorkoutTrackButtonState.Loading,
        onValueIncrementWt = {},
        onValueDecrementWt = {},
        onValueChangeWt = {},
        onValueIncrementReps = { },
        onValueDecrementReps = {},
        onValueChangeReps = { },
        onWorkoutSetClick = {},
    ){
        override fun unchangedCopy(): WorkoutTrackUIState = this // since this's an object
    }

    data class CreateNew(
        val workoutSet: WorkoutSet,
        val onSave: callback = {},
        val onClear: callback = {},
        val incrementWt: callback = {},
        val decrementWt: callback = {},
        val changeWt: callbackWParam<Int> = {},
        val incrementReps: callback = {},
        val decrementReps: callback = {},
        val changeReps: callbackWParam<Int> = {},
        val workoutSetClick: callbackWParam<WorkoutSet> = {},
    ) : WorkoutTrackUIState(
        set = workoutSet,
        button1State = WorkoutTrackButtonState.Save(onSave),
        button2State = WorkoutTrackButtonState.Clear(onClear),
        onValueIncrementWt = incrementWt,
        onValueDecrementWt = decrementWt,
        onValueChangeWt = changeWt,
        onValueIncrementReps = incrementReps,
        onValueDecrementReps = decrementReps,
        onValueChangeReps = changeReps,
        onWorkoutSetClick = workoutSetClick,
    ){
        override fun unchangedCopy(): WorkoutTrackUIState = copy()
    }

    data class EditExisting(
        val workoutSet: WorkoutSet,
        val onSave: callback = {},
        val onDelete: callback = {},
        val incrementWt: callback = {},
        val decrementWt: callback = {},
        val changeWt: callbackWParam<Int> = {},
        val incrementReps: callback = {},
        val decrementReps: callback = {},
        val changeReps: callbackWParam<Int> = {},
        val setSelectedColor: Color = Color(0x80A5D6A7),
        val workoutSetClick: callbackWParam<WorkoutSet> = {},
    ) : WorkoutTrackUIState(
        set = workoutSet,
        button1State = WorkoutTrackButtonState.Edit(onSave),
        button2State = WorkoutTrackButtonState.Delete(onDelete),
        onValueIncrementWt = incrementWt,
        onValueDecrementWt = decrementWt,
        onValueChangeWt = changeWt,
        onValueIncrementReps = incrementReps,
        onValueDecrementReps = decrementReps,
        onValueChangeReps = changeReps,
        onWorkoutSetClick = workoutSetClick,
    ){
        override fun unchangedCopy(): WorkoutTrackUIState = copy()
    }

}

/**
 * Button UI State to be used for displaying workout track buttons. as the name suggests
 */
sealed class WorkoutTrackButtonState(
    val text: String,
    val color: Color,
    val onClick: callback, // empty for default.
) {
    data class Save(val callback: callback = {}) :
        WorkoutTrackButtonState("SAVE", Color(0xFF2196F3), onClick = callback) // blue

    data class Clear(val callback: callback = {}) :
        WorkoutTrackButtonState("CLEAR", Color(0xFFEF5350), onClick = callback) // something red

    data class Edit(val callback: callback = {}) :
        WorkoutTrackButtonState("EDIT", Color(0xFF26A69A), onClick = callback) // green

    data class Delete(val callback: callback = {}) :
        WorkoutTrackButtonState("DELETE", Color(0xFFEF5350), onClick = callback) // something red

    object Loading : WorkoutTrackButtonState("Loading ...", Color(0xFFF9A825), onClick = {}) // or-ange
}
