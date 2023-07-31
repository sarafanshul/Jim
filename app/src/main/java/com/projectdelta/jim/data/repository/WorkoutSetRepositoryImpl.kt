package com.projectdelta.jim.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.projectdelta.jim.data.local.dao.WorkoutSetDao
import com.projectdelta.jim.data.model.entity.WorkoutSet
import com.projectdelta.jim.data.model.relation.WorkoutSetAndWorkout
import com.projectdelta.jim.di.qualifiers.IODispatcher
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.util.Constants.PagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class WorkoutSetRepositoryImpl(
    private val dao: WorkoutSetDao,
    @IODispatcher private val workerDispatcher: CoroutineDispatcher,
) : WorkoutSetRepository {

    override fun insert(obj: WorkoutSet): Long =
        dao.insert(obj)

    override fun insert(vararg obj: WorkoutSet): List<Long> =
        dao.insert(*obj)

    override fun insertAll(objects: List<WorkoutSet>): List<Long> =
        dao.insertAll(objects)

    override fun update(obj: WorkoutSet) =
        dao.update(obj)

    override fun delete(obj: WorkoutSet) =
        dao.delete(obj)

    override fun getById(id: BaseId): Flow<WorkoutSet?> =
        dao.getById(id).flowOn(workerDispatcher)

    override fun getAll(): Flow<List<WorkoutSet>> =
        dao.getAll().flowOn(workerDispatcher)

    override fun getAllPaged(): Flow<PagingData<WorkoutSet>> =
        Pager(
            config = PagingSource.defaultPagingConfig,
            pagingSourceFactory = { dao.getAllPaged() }
        ).flow.flowOn(workerDispatcher)

    override fun getWorkoutSetAndWorkoutById(id: BaseId): Flow<List<WorkoutSetAndWorkout>> =
        dao.getWorkoutSetAndWorkoutById(id).flowOn(workerDispatcher)
}
