package com.projectdelta.jim.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.projectdelta.jim.TestConstants
import com.projectdelta.jim.util.TimeUtil
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
    private lateinit var db: JimDatabase

    private val workoutSessionIds = mutableListOf<Long>()

    private fun createSessionByDay(day: Int) = TestConstants
        .session
        .copy(
            timeMs = TimeUtil
                .dayToMilliseconds(day)
        )

    @Before
    fun createDb() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, JimDatabase::class.java
        ).build()
        workoutSessionDao = db.workoutSessionDao()

        for (i in 1..10) { // 10 mock sessions
            workoutSessionIds.add(workoutSessionDao.insert(createSessionByDay(i)))
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() = runTest {
        db.clearAllTables()
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun readNotNullWorkoutTest() = runTest {

        for (id in workoutSessionIds) {
            val ex = workoutSessionDao.getById(id.toInt()).first()
            assertNotNull(ex)
        }
    }

    @Test
    @Throws(Exception::class)
    fun readNullWorkoutTest() = runTest {

        for (id in listOf(1231, 3131231, 3131231)) {
            val ex = workoutSessionDao.getById(id).first()
            assertNull(ex)
        }
    }

    @Test
    @Throws(Exception::class)
    fun readAllWorkoutTest() = runTest {

        val workoutSessions = workoutSessionDao.getAllSessions().first()
        // checks if all exercises are inserted properly
        assertTrue(workoutSessions.size == workoutSessionIds.size)
    }

    @Test
    @Throws(Exception::class)
    fun readWorkoutByTimeRanged() = runTest {
        // at setup we have one workout session for each day form 0 to 10
        val start = 5
        val end = 8
        val sessions = workoutSessionDao.getByTimeRange(
            TimeUtil.dayToMilliseconds(start),
            TimeUtil.dayToMilliseconds(end)
        ).first()

        assertTrue(sessions.size == (end - start) + 1)
    }

}

