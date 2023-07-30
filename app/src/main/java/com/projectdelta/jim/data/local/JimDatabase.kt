package com.projectdelta.jim.data.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.projectdelta.jim.R
import com.projectdelta.jim.data.local.dao.ExerciseDao
import com.projectdelta.jim.data.local.dao.WorkoutDao
import com.projectdelta.jim.data.local.dao.WorkoutSessionDao
import com.projectdelta.jim.data.local.dao.WorkoutSetDao
import com.projectdelta.jim.data.model.entity.Exercise
import com.projectdelta.jim.data.model.ExerciseWrapper
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSession
import com.projectdelta.jim.data.model.entity.WorkoutSet
import com.projectdelta.jim.util.Constants.Database.VERSION
import com.projectdelta.jim.util.Constants.Database.NAME
import com.projectdelta.jim.util.Converters
import com.projectdelta.jim.util.JSONUtils
import java.util.concurrent.Executors

@Database(
    entities = [
        Exercise::class,
        WorkoutSession::class,
        Workout::class,
        WorkoutSet::class,
    ],
    version = VERSION,
    exportSchema = true,
)
@TypeConverters(
    Converters::class,
)
abstract class JimDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutSessionDao(): WorkoutSessionDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutSetDao(): WorkoutSetDao

    companion object {

        @Volatile
        private var INSTANCE: JimDatabase? = null

        fun getInstance(application: Application): JimDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance

            synchronized(this) {
                val instance = buildDatabase(application)
                INSTANCE = instance
                return instance
            }
        }

        private fun buildDatabase(application: Application): JimDatabase {
            return Room.databaseBuilder(
                application,
                JimDatabase::class.java,
                NAME
            ).addCallback(object : Callback() {
                override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                    super.onDestructiveMigration(db)
                }

                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newSingleThreadExecutor().execute {
                        INSTANCE?.let {
                            // Prepopulate data here if needed
                            val data = JSONUtils.getJsonFileAsClass(
                                application.resources,
                                R.raw.exercises,
                                ExerciseWrapper::class.java
                            )
                            if (data.isPresent)
                                it.exerciseDao().insertAll(data.get().exercises)
                        }
                    }
                }
            }).fallbackToDestructiveMigration()
                .build()
        }

    }
}
