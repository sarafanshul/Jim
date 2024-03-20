package com.projectdelta.jim.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.projectdelta.jim.data.local.dao.WorkoutDao
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.relation.WorkoutWithSetsAndExercise
import com.projectdelta.jim.data.state.SessionState
import com.projectdelta.jim.di.qualifiers.IODispatcher
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.util.paging.Config
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class WorkoutRepositoryImpl(
    private val dao: WorkoutDao,
    @IODispatcher private val workerDispatcher: CoroutineDispatcher,
) : WorkoutRepository {
    override suspend fun insert(obj: Workout): Long =
        dao.insert(obj)

    override suspend fun insert(vararg obj: Workout): List<Long> =
        dao.insert(*obj)

    override suspend fun insertAll(objects: List<Workout>): List<Long> =
        dao.insertAll(objects)

    override suspend fun update(obj: Workout) =
        dao.update(obj)

    override suspend fun delete(obj: Workout) =
        dao.delete(obj)

    override suspend fun getById(id: BaseId): Flow<Workout?> =
        dao.getById(id).flowOn(workerDispatcher)

    override suspend fun getAll(): Flow<List<Workout>> =
        dao.getAllWorkouts().flowOn(workerDispatcher)

    override suspend fun getAllPaged(): Flow<PagingData<Workout>> =
        Pager(
            config = Config.defaultPagingConfig,
            pagingSourceFactory = { dao.getAllWorkoutsPaged() }
        ).flow.flowOn(workerDispatcher)

    override suspend fun getWorkoutWithSetsAndExerciseById(id: BaseId): Flow<SessionState<WorkoutWithSetsAndExercise>> =
        dao.getWorkoutWithSetsAndExerciseById(id).flowOn(workerDispatcher).map {
            if( it.isNotEmpty() )
                SessionState.Session(it.first())
            else
                SessionState.Empty
        }.distinctUntilChanged()
            .flowOn(workerDispatcher)

    override suspend fun getAllWorkoutWithSetsAndExercise(): Flow<List<WorkoutWithSetsAndExercise>> =
        dao.getAllWorkoutWithSetsAndExercise().flowOn(workerDispatcher)

    override suspend fun getAllWorkoutWithSetsAndExercisePaged(): Flow<PagingData<WorkoutWithSetsAndExercise>> =
        Pager(
            config = Config.defaultPagingConfig,
            pagingSourceFactory = { dao.getAllWorkoutWithSetsAndExercisePaged() }
        ).flow.flowOn(workerDispatcher)
}
