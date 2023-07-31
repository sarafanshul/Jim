package com.projectdelta.jim.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.projectdelta.jim.data.local.dao.WorkoutDao
import com.projectdelta.jim.data.local.dao.WorkoutSessionDao
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSession
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class WorkoutSessionDaoTest {
    private lateinit var workoutSessionDao: WorkoutSessionDao
    private lateinit var workoutDao: WorkoutDao
    private lateinit var db: JimDatabase

    data class SessionWWorkout(val id: Int, val workouts: MutableList<Int> = mutableListOf())

    private val workoutSessionIds = mutableListOf<SessionWWorkout>()

    private fun createSessionByDay(day: Int) = WorkoutSession(day)

    @Before
    fun createDb() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, JimDatabase::class.java
        ).build()
        workoutSessionDao = db.workoutSessionDao()
        workoutDao = db.workoutDao()

        for (i in 1..10) { // 10 mock sessions
            val wws = SessionWWorkout(workoutSessionDao.insert(createSessionByDay(i)).toInt())

            for (j in 0..7)
                wws.workouts.add(workoutDao.insert(Workout(sessionId = wws.id)).toInt())

            workoutSessionIds.add(wws)
        }
    }

    @After
    fun closeDb() = runTest {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun readNotNullWorkoutTest() = runTest {

        for (session in workoutSessionIds) {
            val ex = workoutSessionDao.getById(session.id).first()
            assertNotNull(ex)
        }
    }

    @Test
    fun readNullWorkoutTest() = runTest {

        for (id in listOf(1231, 3131231, 3131231)) {
            val ex = workoutSessionDao.getById(id).first()
            assertNull(ex)
        }
    }

    @Test
    fun readAllWorkoutTest() = runTest {

        val workoutSessions = workoutSessionDao.getAllSessions().first()
        // checks if all exercises are inserted properly
        assertTrue(workoutSessions.size == workoutSessionIds.size)
    }

    @Test
    fun workoutByTimeRangedTest() = runTest {
        // at setup we have one workout session for each day form 0 to 10
        val start = 5
        val end = 8
        val sessions = workoutSessionDao.getByIdRanged(
            start, end,
        ).first()

        assertTrue(sessions.size == (end - start) + 1)
    }

    @Test
    fun sessionWithWorkoutsTest() = runTest {
        for (session in workoutSessionIds) {
            val wws = workoutSessionDao.getSessionWithWorkoutsById(session.id).first()
            assertTrue(wws.size == 1)

            assertTrue(session.workouts.size == wws[0].workouts.size)

            for (idx in session.workouts.indices) {
                assertTrue(session.workouts[idx] == wws[0].workouts[idx].id)
            }
        }
    }

}

