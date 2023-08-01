package com.projectdelta.jim.data.model.entity

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.projectdelta.jim.data.model.relation.SessionWithWorkoutWithSets
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.util.Constants.Table.WORKOUT_SESSION_TABLE
import kotlinx.parcelize.Parcelize

/**
 * A Session of workout of a day.
 */
@Keep
@Parcelize
@Entity(tableName = WORKOUT_SESSION_TABLE)
data class WorkoutSession(

    /**
     * [PrimaryKey] will hold time (Day of workouts),
     * hence can be unique.
     *
     * **One Day can have only one workout session**
     */
    @PrimaryKey(autoGenerate = false)
    override var id: BaseId = 0,

) : BaseDBModel(), Parcelable


/**
 * Preview provider for [WorkoutSession]
 */
class WSPreviewParameterProvider : PreviewParameterProvider<WorkoutSession>{
    override val values = generateSequence(WorkoutSession(0)) {
        WorkoutSession(it.id + 1)
    }
}
