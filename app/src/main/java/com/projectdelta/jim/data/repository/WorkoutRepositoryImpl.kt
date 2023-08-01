package com.projectdelta.jim.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.projectdelta.jim.data.local.dao.WorkoutDao
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.relation.WorkoutWithSetsAndExercise
import com.projectdelta.jim.di.qualifiers.IODispatcher
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.util.Constants.PagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class WorkoutRepositoryImpl(
    private val dao: WorkoutDao,
    @IODispatcher private val workerDispatcher: CoroutineDispatcher,
) : WorkoutRepository {
    override fun insert(obj: Workout): Long =
        dao.insert(obj)

    override fun insert(vararg obj: Workout): List<Long> =
        dao.insert(*obj)

    override fun insertAll(objects: List<Workout>): List<Long> =
        dao.insertAll(objects)

    override fun update(obj: Workout) =
        dao.update(obj)

    override fun delete(obj: Workout) =
        dao.delete(obj)

    override fun getById(id: BaseId): Flow<Workout?> =
        dao.getById(id).flowOn(workerDispatcher)

    override fun getAll(): Flow<List<Workout>> =
        dao.getAllWorkouts().flowOn(workerDispatcher)

    override fun getAllPaged(): Flow<PagingData<Workout>> =
        Pager(
            config = PagingSource.defaultPagingConfig,
            pagingSourceFactory = { dao.getAllWorkoutsPaged() }
        ).flow.flowOn(workerDispatcher)

    override fun getWorkoutWithSetsAndExerciseById(id: BaseId): Flow<List<WorkoutWithSetsAndExercise>> =
        dao.getWorkoutWithSetsAndExerciseById(id).flowOn(workerDispatcher)

    override fun getAllWorkoutWithSetsAndExercise(): Flow<List<WorkoutWithSetsAndExercise>> =
        dao.getAllWorkoutWithSetsAndExercise().flowOn(workerDispatcher)

    override fun getAllWorkoutWithSetsAndExercisePaged(): Flow<PagingData<WorkoutWithSetsAndExercise>> =
        Pager(
            config = PagingSource.defaultPagingConfig,
            pagingSourceFactory = { dao.getAllWorkoutWithSetsAndExercisePaged() }
        ).flow.flowOn(workerDispatcher)
}
