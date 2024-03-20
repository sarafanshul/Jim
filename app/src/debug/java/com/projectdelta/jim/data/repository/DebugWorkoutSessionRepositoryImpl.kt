package com.projectdelta.jim.data.repository

import androidx.paging.PagingData
import com.projectdelta.jim.data.local.dao.WorkoutSessionDao
import com.projectdelta.jim.data.model.entity.WorkoutSession
import com.projectdelta.jim.data.model.relation.SessionWithWorkoutWithSets
import com.projectdelta.jim.data.model.relation.SessionWithWorkouts
import com.projectdelta.jim.data.state.SessionState
import com.projectdelta.jim.di.qualifiers.IODispatcher
import com.projectdelta.jim.util.BaseId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class DebugWorkoutSessionRepositoryImpl (
    private val dao: WorkoutSessionDao,
    @IODispatcher private val workerDispatcher: CoroutineDispatcher,
) : WorkoutSessionRepository {

    override suspend fun insert(obj: WorkoutSession): Long {
        TODO("Not yet implemented")
    }

    override suspend fun insert(vararg obj: WorkoutSession): List<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(objects: List<WorkoutSession>): List<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun update(obj: WorkoutSession) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(obj: WorkoutSession) {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: BaseId): Flow<WorkoutSession?> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): Flow<List<WorkoutSession>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPaged(): Flow<PagingData<WorkoutSession>> {
        TODO("Not yet implemented")
    }

    override suspend fun getByIdRanged(start: BaseId, end: BaseId): Flow<List<WorkoutSession>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSessionWithWorkoutsById(id: BaseId): Flow<List<SessionWithWorkouts>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSessionsWithWorkouts(): Flow<List<SessionWithWorkouts>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSessionsWithWorkoutsPaged(): Flow<PagingData<SessionWithWorkouts>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSessionWithWorkoutsWithSets(id: BaseId): Flow<List<SessionWithWorkoutWithSets>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSessionWithWorkoutsWithSets(): Flow<List<SessionWithWorkoutWithSets>> {
        TODO("Not yet implemented")
    }

    override fun getAllSessionWithWorkoutsWithSetsPaged(): Flow<PagingData<SessionState<SessionWithWorkoutWithSets>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSessionByDay(day: Int): Flow<SessionState<WorkoutSession>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSessionWithWorkoutsByDay(day: Int): Flow<SessionState<SessionWithWorkouts>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSessionWithWorkoutWithSetsByDay(day: Int): Flow<SessionState<SessionWithWorkoutWithSets>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSessionWithWorkoutWithSetsByDayRanged(startDay: Int, endDay: Int): Flow<List<SessionState<SessionWithWorkoutWithSets>>> {
        TODO("Not yet implemented")
    }
}
