package com.projectdelta.jim.util

import android.content.Context
import android.content.res.Resources
import androidx.annotation.RawRes
import com.projectdelta.jim.data.model.ExerciseWrapper
import com.projectdelta.jim.data.model.entity.Exercise
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSession
import com.projectdelta.jim.data.model.entity.WorkoutSet
import java.io.FileNotFoundException
import java.io.IOException
import kotlin.random.Random

@Suppress("unused")
object MockDebugData {

    fun prePopulateDatabaseMock(
        resources: Resources,
        @RawRes exerciseRes: Int,
        addEx : (Exercise) -> Long,
        addSes : (WorkoutSession) -> Long,
        addWorkout: (Workout) -> Long,
        addSet: (WorkoutSet) -> Long,
    ) {
        val data = JSONUtils.getJsonFileAsClass(resources, exerciseRes, ExerciseWrapper::class.java)
        if( !data.isPresent )
            return

        val exercises = data.get().exercises
        for(i in exercises.indices)
            exercises[i].id = addEx(exercises[i]).toInt() // insert to db

        for( day in 19500 .. 19600){
            if( Random.nextInt() % 3 == 0 )
                continue
            val session = WorkoutSession(
                id = day,
            )
            // insert session to db
            val sessionId = addSes(session).toInt()
            for (i in 0 .. Random.nextInt(1, 15)){
                val randEx = exercises.random()
                val workout = Workout(
                    sessionId = sessionId,
                    exerciseId = randEx.id,
                )
                // insert workout to db
                val workoutId = addWorkout(workout).toInt()
                for (j in 0 .. Random.nextInt(1, 10)){
                    val set = WorkoutSet(
                        workoutId = workoutId,
                        exerciseId = randEx.id,
                        note = if(Random.nextBoolean() && randEx.instructions.isNotEmpty())
                            randEx.instructions.first()
                        else "",
                        weight = Random.nextInt(10, 180).toDouble(),
                        reps = Random.nextInt(3, 20),
                    )
                    addSet(set)
                }
            }
        }
    }

    fun writeToFile(context: Context, fileName: String, jsonString: String?): Boolean {
        return try {
            val fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            if (jsonString != null) {
                fos.write(jsonString.toByteArray())
            }
            fos.close()
            true
        } catch (fileNotFound: FileNotFoundException) {
            false
        } catch (ioException: IOException) {
            false
        }
    }


}
