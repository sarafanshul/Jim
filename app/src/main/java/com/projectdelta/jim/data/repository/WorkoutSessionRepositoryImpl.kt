package com.projectdelta.jim.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.projectdelta.jim.data.local.dao.WorkoutSessionDao
import com.projectdelta.jim.data.model.entity.WorkoutSession
import com.projectdelta.jim.di.qualifiers.IODispatcher
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.util.Constants
import com.projectdelta.jim.util.TimeUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber

class WorkoutSessionRepositoryImpl(
    private val dao: WorkoutSessionDao,
    @IODispatcher private val workerDispatcher: CoroutineDispatcher,
) : WorkoutSessionRepository {

    override fun getSessionByDay(day: Int): Flow<WorkoutSessionState> {
        Timber.d("fetched for day: $day")
        val session = getByTime( TimeUtil.dayToMilliseconds(day) )
        return session.map {
            if(it.isNotEmpty())
                WorkoutSessionState.Session(it.first()) // FIXME : a day can only have one session
            else
                WorkoutSessionState.NoSession
        }.flowOn(workerDispatcher)
    }

    override fun getAllPaged(): Flow<PagingData<WorkoutSession>> {
        return Pager(
            config = Constants.PagingSource.defaultPagingConfig,
            pagingSourceFactory = {
                dao.getAllSessionsPaged()
            }
        ).flow
    }

    override fun getById(id: BaseId): Flow<WorkoutSession?> {
        return dao.getById(id).flowOn(workerDispatcher)
    }

}
