package com.projectdelta.jim.data.repository

import androidx.paging.PagingData
import com.projectdelta.jim.data.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    /**
     * Fetches all [Exercise] from database
     * @return [List] of all [Exercise]
     */
    fun getAllExercises() : Flow<List<Exercise>>

    /**
     * Fetches all [Exercise] in database, Paged, ORDER_BY ASC
     * @return [Flow]<[PagingData]<[Exercise]>>
     */
    fun getAllExercisesPaged() : Flow<PagingData<Exercise>>
}
