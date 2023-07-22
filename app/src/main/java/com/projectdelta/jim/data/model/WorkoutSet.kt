package com.projectdelta.jim.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// TODO add metric system conversion

data class WorkoutSet(

    @SerializedName("note")
    val note : String = "",

    @SerializedName("weight")
    val weight : Double = 0.0,

    @SerializedName("reps")
    val reps : Int = 0,

    @SerializedName("durationMs")
    val durationMs : Long = 0,
) : Serializable
