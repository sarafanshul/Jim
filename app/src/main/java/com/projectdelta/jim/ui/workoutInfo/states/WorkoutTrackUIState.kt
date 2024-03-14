package com.projectdelta.jim.ui.workoutInfo.states

import androidx.compose.ui.graphics.Color
import com.projectdelta.jim.data.model.entity.WorkoutSet
import com.projectdelta.jim.util.unitCallback
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
    val onValueIncrementWt: unitCallback,
    val onValueDecrementWt: unitCallback,
    val onValueChangeWt: callbackWParam<Int>,
    val onValueIncrementReps: unitCallback,
    val onValueDecrementReps: unitCallback,
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
        val onSave: unitCallback = {},
        val onClear: unitCallback = {},
        val incrementWt: unitCallback = {},
        val decrementWt: unitCallback = {},
        val changeWt: callbackWParam<Int> = {},
        val incrementReps: unitCallback = {},
        val decrementReps: unitCallback = {},
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
        val onSave: unitCallback = {},
        val onDelete: unitCallback = {},
        val incrementWt: unitCallback = {},
        val decrementWt: unitCallback = {},
        val changeWt: callbackWParam<Int> = {},
        val incrementReps: unitCallback = {},
        val decrementReps: unitCallback = {},
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
    val onClick: unitCallback, // empty for default.
) {
    data class Save(val callback: unitCallback = {}) :
        WorkoutTrackButtonState("SAVE", Color(0xFF2196F3), onClick = callback) // blue

    data class Clear(val callback: unitCallback = {}) :
        WorkoutTrackButtonState("CLEAR", Color(0xFFEF5350), onClick = callback) // something red

    data class Edit(val callback: unitCallback = {}) :
        WorkoutTrackButtonState("EDIT", Color(0xFF26A69A), onClick = callback) // green

    data class Delete(val callback: unitCallback = {}) :
        WorkoutTrackButtonState("DELETE", Color(0xFFEF5350), onClick = callback) // something red

    object Loading : WorkoutTrackButtonState("Loading ...", Color(0xFFF9A825), onClick = {}) // or-ange
}
