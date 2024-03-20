package com.projectdelta.jim.data.model.entity

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.util.Constants.Table.WORKOUT_SET_TABLE
import kotlinx.parcelize.Parcelize

/**
 * A Set table to keep track of sets
 */
@Keep
@Parcelize
@Entity(tableName = WORKOUT_SET_TABLE)
data class WorkoutSet(

    /**
     * Id of this Set
     */
    @PrimaryKey(autoGenerate = true)
    override var id: BaseId = 0,

    @SerializedName("note")
    var note: String = "",

    // todo : Add implementation of metrics conversion
    @SerializedName("weight")
    var weight: Double = 0.0,

    @SerializedName("reps")
    var reps: Int = 0,

    @SerializedName("durationMs")
    val durationMs: Long = 0,

    /**
     * Id of respective [Workout] this Set is part of
     */
    @SerializedName("workoutId")
    val workoutId: BaseId = 0,

    /**
     * Id of [Exercise] performed in this Set.
     */
    @SerializedName("exerciseId")
    val exerciseId: BaseId = 0,

) : BaseModel(), Parcelable {
    companion object{
        fun buildNew(
            workoutId: BaseId,
            exerciseId: BaseId,
        ) = WorkoutSet(
            workoutId = workoutId,
            exerciseId = exerciseId
        )
    }
}

/**
 * Preview provider for [WorkoutSet]
 */
class WSTPreviewParameterProvider : PreviewParameterProvider<WorkoutSet> {
    override val values = sequenceOf(
        WorkoutSet(
            id = 1,
            weight = 150.0,
            reps = 3,
        )
    )
}
