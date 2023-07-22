package com.projectdelta.jim.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.util.Constants.Table.WORKOUT_SESSION_TABLE
import kotlin.random.Random

/**
 * A collection of [Workout], this contains all workout for a day.
 */
@Keep
@Entity(tableName = WORKOUT_SESSION_TABLE)
data class WorkoutSession(

    @PrimaryKey(autoGenerate = false)
    override val id: BaseId = Random.nextInt(),

    @[SerializedName("timeMs") ColumnInfo(name = "timeMs")]
    val timeMs: Long = 0,

    @[SerializedName("workouts") ColumnInfo(name = "workouts")]
    val workouts : List<Workout> = listOf()

) : BaseModel()
