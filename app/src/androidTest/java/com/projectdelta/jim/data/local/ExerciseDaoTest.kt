package com.projectdelta.jim.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.projectdelta.jim.TestConstants
import com.projectdelta.jim.data.local.dao.ExerciseDao
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
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class ExerciseDaoTest {
    private lateinit var exerciseDao: ExerciseDao
    private lateinit var db: JimDatabase

    private val exerciseIds = mutableListOf<Long>()

    private fun createExerciseByName(name: String) = TestConstants
        .exercise
        .copy(
            name = name
        )

    @Before
    fun createDb() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, JimDatabase::class.java
        ).build()
        exerciseDao = db.exerciseDao()

        for (i in 1..5) { // mock sessions
            exerciseIds.add(exerciseDao.insert(createExerciseByName(Random.nextInt().toString())))
        }

        exerciseIds.add(exerciseDao.insert(createExerciseByName("Curls")))
        exerciseIds.add(exerciseDao.insert(createExerciseByName("Hamstring Curls")))
    }

    @After
    @Throws(IOException::class)
    fun closeDb() = runTest {
        db.clearAllTables()
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun readNotNullExerciseTest() = runTest {

        for (id in exerciseIds) {
            val ex = exerciseDao.getById(id.toInt()).first()
            assertNotNull(ex)
        }
    }

    @Test
    @Throws(Exception::class)
    fun readNullExerciseTest() = runTest {

        for (id in listOf(1231, 3131231, 3131231)) {
            val ex = exerciseDao.getById(id).first()
            assertNull(ex)
        }
    }

    @Test
    @Throws(Exception::class)
    fun readAllExerciseTest() = runTest {

        val exercises = exerciseDao.getAllExercises().first()
        // checks if all exercises are inserted properly
        assertTrue(exercises.size == exerciseIds.size)
    }

    @Test
    @Throws(Exception::class)
    fun readAllExercisesByNameLike() = runTest {
        val curls = exerciseDao.getByNameLike("Cur").first()
        assertNotNull(curls)
        assertTrue(curls.size == 2) // Curls, Hamstring Curls
    }

    @Test
    @Throws(Exception::class)
    fun readAllExercisesByNameLikeNull() = runTest {
        val curls = exerciseDao.getByNameLike("Squats").first()
        assertNotNull(curls)
        assertTrue(curls.isEmpty()) // 404
    }

    @Test
    @Throws(Exception::class)
    fun readAllExercisesByNameLikeCase() = runTest { // query shouldn't be case sensitive
        val curls = exerciseDao.getByNameLike("cur").first()
        assertNotNull(curls)
        assertTrue(curls.size == 2) // Curls, Hamstring Curls
    }

}
