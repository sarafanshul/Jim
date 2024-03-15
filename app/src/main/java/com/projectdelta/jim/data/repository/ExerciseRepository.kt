package com.projectdelta.jim.data.repository

import androidx.paging.PagingData
import com.projectdelta.jim.data.model.entity.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository : BaseDBRepository<Exercise> {

    /**
     * Fetches all [Exercise] where name `LIKE` [substring]
     * @param substring substring to match
     * @return [List] of all [Exercise] found
     */
    suspend fun getByNameLike(substring: String): Flow<List<Exercise>>

    /**
     * Fetches all [Exercise] where name `LIKE` [substring] Paged
     * @param substring substring to match
     * @return [List] of all [Exercise] found
     */
    suspend fun getByNameLikePaged(substring: String): Flow<PagingData<Exercise>>
}
