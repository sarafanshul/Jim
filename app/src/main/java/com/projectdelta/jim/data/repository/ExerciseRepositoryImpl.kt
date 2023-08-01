package com.projectdelta.jim.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.projectdelta.jim.data.local.dao.ExerciseDao
import com.projectdelta.jim.data.model.entity.Exercise
import com.projectdelta.jim.di.qualifiers.IODispatcher
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.util.Constants.PagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ExerciseRepositoryImpl(
    private val dao: ExerciseDao,
    @IODispatcher private val workerDispatcher: CoroutineDispatcher,
) : ExerciseRepository {

    override fun insert(obj: Exercise): Long =
        dao.insert(obj)

    override fun insert(vararg obj: Exercise): List<Long> =
        dao.insert(*obj)

    override fun insertAll(objects: List<Exercise>): List<Long> =
        dao.insertAll(objects)

    override fun update(obj: Exercise) =
        dao.update(obj)

    override fun delete(obj: Exercise) =
        dao.delete(obj)

    override fun getById(id: BaseId): Flow<Exercise?> =
        dao.getById(id).flowOn(workerDispatcher)

    override fun getByNameLike(substring: String): Flow<List<Exercise>> =
        dao.getByNameLike(substring).flowOn(workerDispatcher)

    override fun getByNameLikePaged(substring: String): Flow<PagingData<Exercise>> =
        Pager(
            config = PagingSource.defaultPagingConfig,
            pagingSourceFactory = { dao.getByNameLikePaged(substring) }
        ).flow.flowOn(workerDispatcher)

    override fun getAll(): Flow<List<Exercise>> =
        dao.getAllExercises().flowOn(workerDispatcher)

    override fun getAllPaged(): Flow<PagingData<Exercise>> =
        Pager(
            config = PagingSource.defaultPagingConfig,
            pagingSourceFactory = { dao.getAllExercisesPaged() }
        ).flow.flowOn(workerDispatcher)
}
