package com.projectdelta.jim.data.local

import androidx.room.Dao
import androidx.room.Query
import com.projectdelta.jim.data.model.BaseId
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
    fun getById(id : BaseId) : Flow<Exercise?>

    /**
     * Fetches [Exercise] by names
     * @param name [Exercise.name]'s to fetch
     * @return [List] of [Exercise]
     */
    @Query("SELECT * FROM $EXERCISE_TABLE WHERE name = :name")
    fun getByName(name : String) : Flow<List<Exercise>>

    /**
     * Fetches all [Exercise] from database
     * @return [List] of all [Exercise]
     */
    @Query("SELECT * FROM $EXERCISE_TABLE ORDER BY name ASC")
    fun getAllExercises() : Flow<List<Exercise>>

}
