package com.projectdelta.jim.data.repository

import com.projectdelta.jim.data.local.WorkoutSessionDao
import com.projectdelta.jim.data.model.WorkoutSession
import com.projectdelta.jim.di.qualifiers.IODispatcher
import com.projectdelta.jim.util.BaseId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class WorkoutSessionRepositoryImpl(
    private val dao: WorkoutSessionDao,
    @IODispatcher private val workerDispatcher: CoroutineDispatcher,
) : WorkoutSessionRepository {

    override fun getById(id: BaseId): Flow<WorkoutSession?> {
        return dao.getById(id).flowOn(workerDispatcher)
    }

    override fun getByTime(timeMs: Long): Flow<List<WorkoutSession>> {
        return dao.getByTime(timeMs).flowOn(workerDispatcher)
    }

}
