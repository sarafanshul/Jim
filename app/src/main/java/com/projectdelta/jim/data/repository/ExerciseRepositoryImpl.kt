package com.projectdelta.jim.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.projectdelta.jim.data.local.dao.ExerciseDao
import com.projectdelta.jim.data.model.entity.Exercise
import com.projectdelta.jim.di.qualifiers.IODispatcher
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.util.paging.Config
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ExerciseRepositoryImpl(
    private val dao: ExerciseDao,
    @IODispatcher private val workerDispatcher: CoroutineDispatcher,
) : ExerciseRepository {

    override suspend fun insert(obj: Exercise): Long =
        dao.insert(obj)

    override suspend fun insert(vararg obj: Exercise): List<Long> =
        dao.insert(*obj)

    override suspend fun insertAll(objects: List<Exercise>): List<Long> =
        dao.insertAll(objects)

    override suspend fun update(obj: Exercise) =
        dao.update(obj)

    override suspend fun delete(obj: Exercise) =
        dao.delete(obj)

    override suspend fun getById(id: BaseId): Flow<Exercise?> =
        dao.getById(id).flowOn(workerDispatcher)

    override suspend fun getByNameLike(substring: String): Flow<List<Exercise>> =
        dao.getByNameLike(substring).flowOn(workerDispatcher)

    override suspend fun getByNameLikePaged(substring: String): Flow<PagingData<Exercise>> =
        Pager(
            config = Config.defaultPagingConfig,
            pagingSourceFactory = { dao.getByNameLikePaged(substring) }
        ).flow.flowOn(workerDispatcher)

    override suspend fun getAll(): Flow<List<Exercise>> =
        dao.getAllExercises().flowOn(workerDispatcher)

    override suspend fun getAllPaged(): Flow<PagingData<Exercise>> =
        Pager(
            config = Config.defaultPagingConfig,
            pagingSourceFactory = { dao.getAllExercisesPaged() }
        ).flow.flowOn(workerDispatcher)
}
