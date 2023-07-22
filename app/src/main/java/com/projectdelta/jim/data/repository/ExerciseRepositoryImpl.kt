package com.projectdelta.jim.data.repository

import com.projectdelta.jim.data.local.ExerciseDao
import com.projectdelta.jim.data.model.Exercise
import com.projectdelta.jim.di.qualifiers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ExerciseRepositoryImpl(
    private val dao: ExerciseDao,
    @IODispatcher private val workerDispatcher: CoroutineDispatcher,
) : ExerciseRepository{

    override fun getAllExercises(): Flow<List<Exercise>> {
        return dao.getAllExercises().flowOn(workerDispatcher)
    }

}
