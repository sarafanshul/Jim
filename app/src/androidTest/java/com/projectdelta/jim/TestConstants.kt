package com.projectdelta.jim

import com.projectdelta.jim.data.model.entity.Exercise
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSession
import com.projectdelta.jim.data.model.entity.WorkoutSet
import com.projectdelta.jim.util.TimeUtil

object TestConstants{
    private const val day = 1000

    val exercise = Exercise(
        name = "Barbell Row",
        force = "pull",
        mechanic = "compound"
    )
}
