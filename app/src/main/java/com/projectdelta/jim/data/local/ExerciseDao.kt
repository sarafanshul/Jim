package com.projectdelta.jim.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.data.model.Exercise
import com.projectdelta.jim.util.Constants.Table.EXERCISE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao : BaseDao<Exercise> {

    /**
     * Fetches [Exercise] by id
     * @param id unique id of item to fetch
     * @return [Exercise] if found.
     */
    @Query("SELECT * FROM $EXERCISE_TABLE WHERE id = :id")
    fun getById(id: BaseId): Flow<Exercise?>

    /**
     * Fetches [Exercise] by names
     * @param name [Exercise.name]'s to fetch
     * @return [List]<[Exercise]>
     */
    @Query("SELECT * FROM $EXERCISE_TABLE WHERE name = :name")
    fun getByName(name: String): Flow<List<Exercise>>

    /**
     * Fetches all [Exercise] from database
     * @return [List] of all [Exercise]
     */
    @Query("SELECT * FROM $EXERCISE_TABLE ORDER BY name ASC")
    fun getAllExercises(): Flow<List<Exercise>>

    /**
     * Fetches all [Exercise] in database, Paged, ORDER_BY ASC
     * @return [PagingSource]<[Int],[Exercise]>
     */
    @Query("SELECT * FROM $EXERCISE_TABLE ORDER BY name ASC")
    fun getAllExercisesPaged(): PagingSource<Int, Exercise>

    /**
     * Fetches all [Exercise] where name `LIKE` [substring]
     * @param substring substring to match
     * @return [List] of all [Exercise] found
     */
    @Query("SELECT * FROM $EXERCISE_TABLE WHERE name LIKE '%' || :substring || '%'")
    fun getByNameLike(substring: String): Flow<List<Exercise>>

    /**
     * Fetches all [Exercise] where name `LIKE` [substring] Paged
     * @param substring substring to match
     * @return [List] of all [Exercise] found
     */
    @Query("SELECT * FROM $EXERCISE_TABLE WHERE name LIKE '%' || :substring || '%'")
    fun getByNameLikePaged(substring: String): PagingSource<Int, Exercise>

}
