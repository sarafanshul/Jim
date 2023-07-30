package com.projectdelta.jim.data.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.projectdelta.jim.data.model.entity.BaseDBModel
import com.projectdelta.jim.data.model.entity.WorkoutSession
import com.projectdelta.jim.util.BaseId
import kotlinx.coroutines.flow.Flow

/**
 * Base Repository for [BaseDBModel] objects, utilising BaseDao,
 * This has all basic crud operations
 */
interface BaseDBRepository<T : BaseDBModel> {
    /**
     * Inserts item in database
     * @param obj item to be inserted
     * @return new id of inserted object
     */
    fun insert(obj: T): Long

    /**
     * Inserts item in database
     * @param obj item to be inserted
     * @return new id of inserted object
     */
    fun insert(vararg obj: T): List<Long>

    /**
     * Insert a list in the database. If the item already exists, replace it.
     * @param objects the items to be inserted.
     */
    fun insertAll(objects: List<T>): List<Long>

    /**
     * Updates the item in database
     * @param obj item to be updated
     */
    fun update(obj: T)

    /**
     * Deletes the item from database, if exists
     * @param obj item to be deleted
     */
    fun delete(obj: T)

    /**
     * Fetches [T] by id
     * @param id unique id of item to fetch
     * @return [WorkoutSession] if found.
     */
    fun getById(id: BaseId): Flow<T?>

    /**
     * Fetches all [T] from Table
     * @return [List] of all [T]
     */
    fun getAll(): Flow<List<T>>

    /**
     * Fetches all [T] in Table, Paged, ORDER_BY ASC
     * @return [PagingSource]<[Int],[T]>
     */
    fun getAllPaged(): Flow<PagingData<T>>

}
