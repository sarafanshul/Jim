package com.projectdelta.jim.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.projectdelta.jim.data.local.ExerciseDao
import com.projectdelta.jim.data.model.Exercise
import com.projectdelta.jim.di.qualifiers.IODispatcher
import com.projectdelta.jim.util.Constants.PagingSource
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

    override fun getAllExercisesPaged(): Flow<PagingData<Exercise>> {
        return Pager(
            config = PagingSource.defaultPagingConfig,
            pagingSourceFactory = {
                dao.getAllExercisesPaged()
            }
        ).flow
    }

}
