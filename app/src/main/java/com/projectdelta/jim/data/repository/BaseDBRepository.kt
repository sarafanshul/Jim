package com.projectdelta.jim.data.repository

import androidx.paging.PagingData
import com.projectdelta.jim.data.model.entity.BaseModel
import com.projectdelta.jim.data.model.entity.WorkoutSession
import com.projectdelta.jim.util.BaseId
import kotlinx.coroutines.flow.Flow

/**
 * Base Repository for [BaseModel] objects, utilising BaseDao,
 * This has all basic crud operations
 */
interface BaseDBRepository<T : BaseModel> {
    /**
     * Inserts item in database
     * @param obj item to be inserted
     * @return new id of inserted object
     */
    suspend fun insert(obj: T): Long

    /**
     * Inserts item in database
     * @param obj item to be inserted
     * @return new id of inserted object
     */
    suspend fun insert(vararg obj: T): List<Long>

    /**
     * Insert a list in the database. If the item already exists, replace it.
     * @param objects the items to be inserted.
     */
    suspend fun insertAll(objects: List<T>): List<Long>

    /**
     * Updates the item in database
     * @param obj item to be updated
     */
    suspend fun update(obj: T)

    /**
     * Deletes the item from database, if exists
     * @param obj item to be deleted
     */
    suspend fun delete(obj: T)

    /**
     * Fetches [T] by id
     * @param id unique id of item to fetch
     * @return [WorkoutSession] if found.
     */
    suspend fun getById(id: BaseId): Flow<T?>

    /**
     * Fetches all [T] from Table
     * @return [List] of all [T]
     */
    suspend fun getAll(): Flow<List<T>>

    /**
     * Fetches all [T] in Table, Paged, ORDER_BY ASC
     * @return [PagingData]<[T]>
     */
    suspend fun getAllPaged(): Flow<PagingData<T>>

}
