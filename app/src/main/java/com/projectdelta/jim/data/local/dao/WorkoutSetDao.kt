package com.projectdelta.jim.data.local.dao

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.projectdelta.jim.data.model.entity.WorkoutSet
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.util.Constants.Table.WORKOUT_SET_TABLE
import kotlinx.coroutines.flow.Flow

/**
 * [Dao] for [WorkoutSet] Table
 */
@Dao
interface WorkoutSetDao : BaseDao<WorkoutSet> {

    /**
     * Fetches [WorkoutSet] by [id]
     * @param id unique id of [WorkoutSet.id]
     * @return [WorkoutSet] if found
     */
    @Query("SELECT * FROM $WORKOUT_SET_TABLE WHERE id = :id")
    fun getById(id: BaseId) : Flow<WorkoutSet?>

    /**
     * Fetches all [WorkoutSet] from Table
     * @return [List] of all [WorkoutSet]
     */
    @Query("SELECT * FROM $WORKOUT_SET_TABLE")
    fun getAll(): Flow<List<WorkoutSet>>

    /**
     * Fetches all [T] in Table, Paged, ORDER_BY ASC
     * @return [PagingSource]<[Int],[T]>
     */
    @Query("SELECT * FROM $WORKOUT_SET_TABLE")
    fun getAllPaged(): Flow<PagingData<WorkoutSet>>

}
