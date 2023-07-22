package com.projectdelta.jim.data.repository

import com.projectdelta.jim.data.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    fun getAllExercises() : Flow<List<Exercise>>
}
