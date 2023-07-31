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
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class WorkoutSetDaoTest {
    private lateinit var workoutDao: WorkoutDao
    private lateinit var setDao: WorkoutSetDao
    private lateinit var db: JimDatabase

    data class SetWWorkout(val id: Int, val workout: Int)

    private val workoutSetIds = mutableListOf<SetWWorkout>()

    @Before
    fun createDb() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, JimDatabase::class.java
        ).build()
        workoutDao = db.workoutDao()
        setDao = db.workoutSetDao()

        for (i in 1..10) { // 10 mock sessions
            val workout = workoutDao.insert(Workout(sessionId = -1)).toInt()
            val set = setDao.insert(WorkoutSet(workoutId = workout)).toInt()

            val wws = SetWWorkout(set, workout)

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
    fun getWorkoutSetAndWorkoutTest() = runTest{
        for ((set, workout) in workoutSetIds){
            val st = setDao.getWorkoutSetAndWorkoutById(set).first()

            assertTrue( st.size == 1 )

            assertNotNull(st[0].workout)

            assertTrue(st[0].workout!!.id == workout)
        }
    }
}
