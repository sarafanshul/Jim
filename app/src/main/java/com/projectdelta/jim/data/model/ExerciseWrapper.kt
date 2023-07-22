package com.projectdelta.jim.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ExerciseWrapper (
    @SerializedName("exercises")
    val exercises: List<Exercise> = listOf()
) : Serializable
