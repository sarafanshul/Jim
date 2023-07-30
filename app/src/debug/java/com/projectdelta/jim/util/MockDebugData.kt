package com.projectdelta.jim.util

import android.content.Context
import android.content.res.Resources
import androidx.annotation.RawRes
import com.projectdelta.jim.data.model.ExerciseWrapper
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSession
import com.projectdelta.jim.data.model.entity.WorkoutSet
import java.io.FileNotFoundException
import java.io.IOException
import kotlin.random.Random


object MockDebugData {
    data class MockDebugDataWrapper(
        val data : List<WorkoutSession> = listOf()
    )

    fun getPrepopulateSessionData(
        resources: Resources,
        @RawRes exerciseRes: Int,
        ): MockDebugDataWrapper {
        val data = JSONUtils.getJsonFileAsClass(resources, exerciseRes, ExerciseWrapper::class.java)
        if( !data.isPresent )
            return MockDebugDataWrapper()

        val exercises = data.get().exercises
        var id = 0
        for(i in exercises.indices)
            exercises[i].id = id++

        val sessions = mutableListOf<WorkoutSession>()
        for( day in 19500 .. 19600){
            if( Random.nextInt() % 3 == 0 )
                continue
            val session = WorkoutSession(
                timeMs = TimeUtil.dayToMilliseconds( day ),
                workouts = List(Random.nextInt(1, 10)){
                    val randEx = exercises.random()
                    Workout(
                        exerciseName = randEx.name,
                        exerciseId = randEx.id,
                        sets = List(Random.nextInt(1, 10)){
                            WorkoutSet(
                                note = if(Random.nextBoolean() && randEx.instructions.isNotEmpty())
                                        randEx.instructions.first()
                                    else "",
                                weight = Random.nextInt(10, 180).toDouble(),
                                reps = Random.nextInt(3, 5),
                            )
                        }
                    )
                }
            )
            sessions.add(session)
        }
        return MockDebugDataWrapper(sessions)
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
