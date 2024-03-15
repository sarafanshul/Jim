package com.projectdelta.jim.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.projectdelta.jim.data.local.dao.WorkoutSessionDao
import com.projectdelta.jim.data.model.entity.WorkoutSession
import com.projectdelta.jim.data.model.relation.SessionWithWorkoutWithSets
import com.projectdelta.jim.data.model.relation.SessionWithWorkouts
import com.projectdelta.jim.data.state.SessionState
import com.projectdelta.jim.di.qualifiers.IODispatcher
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.util.Constants.PagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class WorkoutSessionRepositoryImpl(
    private val dao: WorkoutSessionDao,
    @IODispatcher private val workerDispatcher: CoroutineDispatcher,
) : WorkoutSessionRepository {

    override suspend fun insert(obj: WorkoutSession): Long =
        dao.insert(obj)

    override suspend fun insert(vararg obj: WorkoutSession): List<Long> =
        dao.insert(*obj)

    override suspend fun insertAll(objects: List<WorkoutSession>): List<Long> =
        dao.insertAll(objects)

    override suspend fun update(obj: WorkoutSession) =
        dao.update(obj)

    override suspend fun delete(obj: WorkoutSession) =
        dao.delete(obj)

    override suspend fun getById(id: BaseId): Flow<WorkoutSession?> =
        dao.getById(id).flowOn(workerDispatcher)

    override suspend fun getByIdRanged(start: BaseId, end: BaseId): Flow<List<WorkoutSession>> =
        dao.getByIdRanged(start, end).flowOn(workerDispatcher)

    override suspend fun getSessionWithWorkoutsById(id: BaseId): Flow<List<SessionWithWorkouts>> =
        dao.getSessionWithWorkoutsById(id).flowOn(workerDispatcher)

    override suspend fun getAllSessionsWithWorkouts(): Flow<List<SessionWithWorkouts>> =
        dao.getAllSessionsWithWorkouts().flowOn(workerDispatcher)

    override suspend fun getAllSessionsWithWorkoutsPaged(): Flow<PagingData<SessionWithWorkouts>> =
        Pager(
            config = PagingSource.defaultPagingConfig,
            pagingSourceFactory = { dao.getAllSessionsWithWorkoutsPaged() }
        ).flow.flowOn(workerDispatcher)

    override suspend fun getSessionWithWorkoutsWithSets(id: BaseId): Flow<List<SessionWithWorkoutWithSets>> =
        dao.getSessionWithWorkoutsWithSets(id).flowOn(workerDispatcher)

    override suspend fun getAllSessionWithWorkoutsWithSets(): Flow<List<SessionWithWorkoutWithSets>> =
        dao.getAllSessionWithWorkoutsWithSets().flowOn(workerDispatcher)

    override suspend fun getAllSessionWithWorkoutsWithSetsPaged(): Flow<PagingData<SessionWithWorkoutWithSets>> =
        Pager(
            config = PagingSource.defaultPagingConfig,
            pagingSourceFactory = { dao.getAllSessionWithWorkoutsWithSetsPaged() }
        ).flow.flowOn(workerDispatcher)

    override suspend fun getSessionByDay(day: Int): Flow<SessionState<WorkoutSession>> =
        dao.getById(day).map {
            if (it != null)
                SessionState.Session(it)
            else
                SessionState.Empty
        }.flowOn(workerDispatcher)

    override suspend fun getSessionWithWorkoutsByDay(day: Int): Flow<SessionState<SessionWithWorkouts>> =
        dao.getSessionWithWorkoutsById(day).map {
            if (it.isNotEmpty())
                SessionState.Session(it.first())
            else
                SessionState.Empty
        }.flowOn(workerDispatcher)

    override suspend fun getSessionWithWorkoutWithSetsByDay(day: Int): Flow<SessionState<SessionWithWorkoutWithSets>> =
        dao.getSessionWithWorkoutsWithSets(day).map {
            if (it.isNotEmpty())
                SessionState.Session(it.first())
            else
                SessionState.Empty
        }.distinctUntilChanged()
            .flowOn(workerDispatcher)

    override suspend fun getSessionWithWorkoutWithSetsByDayRanged(
        startDay: Int,
        endDay: Int
    ): Flow<List<SessionState<SessionWithWorkoutWithSets>>> {
        TODO()
    }

    override suspend fun getAll(): Flow<List<WorkoutSession>> =
        dao.getAllSessions().flowOn(workerDispatcher)

    override suspend fun getAllPaged(): Flow<PagingData<WorkoutSession>> =
        Pager(
            config = PagingSource.defaultPagingConfig,
            pagingSourceFactory = { dao.getAllSessionsPaged() }
        ).flow.flowOn(workerDispatcher)
}
