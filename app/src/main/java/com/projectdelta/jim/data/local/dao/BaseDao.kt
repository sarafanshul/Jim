package com.projectdelta.jim.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.projectdelta.jim.data.model.entity.BaseModel

interface BaseDao<in T : BaseModel> {

    /**
     * Inserts item in database
     * @param obj item to be inserted
     * @return new id of inserted object
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T): Long

    /**
     * Inserts item in database
     * @param obj item to be inserted
     * @return new id of inserted object
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg obj: T): List<Long>

    /**
     * Insert a list in the database. If the item already exists, replace it.
     * @param objects the items to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAll( objects : List<T> ) : List<Long>

    /**
     * Updates the item in database
     * @param obj item to be updated
     */
    @Update
    fun update( obj : T )

    /**
     * Deletes the item from database, if exists
     * @param obj item to be deleted
     */
    @Delete
    fun delete( obj : T )
}
