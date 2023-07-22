package com.projectdelta.jim.data.model

import com.google.gson.annotations.SerializedName
import com.projectdelta.jim.util.BaseId
import java.io.Serializable

data class Workout(

    @SerializedName("exerciseName")
    val exerciseName: String = "",

    @SerializedName("exerciseId")
    val exerciseId: BaseId = -1,

    @SerializedName("sets")
    val sets : List<WorkoutSet> = listOf()
) : Serializable