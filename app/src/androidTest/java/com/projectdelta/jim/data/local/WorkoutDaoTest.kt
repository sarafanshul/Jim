package com.projectdelta.jim.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.projectdelta.jim.data.local.dao.WorkoutDao
import com.projectdelta.jim.data.local.dao.WorkoutSetDao
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class WorkoutDaoTest {
    private lateinit var workoutDao: WorkoutDao
    private lateinit var setDao: WorkoutSetDao
    private lateinit var db: JimDatabase

    data class WorkoutWSet(val id: Int, val sets: MutableList<Int> = mutableListOf())

    private val workoutSetIds = mutableListOf<WorkoutWSet>()

    @Before
    fun createDb() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, JimDatabase::class.java
        ).build()
        workoutDao = db.workoutDao()
        setDao = db.workoutSetDao()

        for (i in 1..10) { // 10 mock sessions
            val wws = WorkoutWSet(workoutDao.insert(Workout(sessionId = -1)).toInt())

            for (j in 0..7)
                wws.sets.add(setDao.insert(WorkoutSet(workoutId = wws.id)).toInt())

            workoutSetIds.add(wws)
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() = runTest {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun getWorkoutWithSetsAndExerciseTest() = runTest {
        for(workout in workoutSetIds){
            val ws = workoutDao.getWorkoutWithSetsAndExerciseById(workout.id).first()

            assertTrue(ws.size == 1)
            assertNull(ws[0].exercise) // since we didn't provide

            for(idx in workout.sets.indices){
                assertTrue(workout.sets[idx] == ws[0].sets[idx].id)
            }
        }
    }

}
