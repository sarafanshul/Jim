package com.projectdelta.jim.data.model.entity

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.util.Constants.Table.WORKOUT_TABLE
import kotlinx.parcelize.Parcelize

/**
 * Denotes a Workout, mapped with respective objects.
 */
@Keep
@Entity(tableName = WORKOUT_TABLE)
@Parcelize
data class Workout(

    /**
     * id of this Workout
     */
    @PrimaryKey(autoGenerate = true)
    override var id: BaseId = 0,

    /**
     * Session's Id, used for DB relations.
     */
    @SerializedName("sessionId")
    val sessionId: BaseId = 0,

    /**
     * Exercise Id of current workout.
     */
    @SerializedName("exerciseId")
    val exerciseId: BaseId = 0,

) : BaseModel(), Parcelable
