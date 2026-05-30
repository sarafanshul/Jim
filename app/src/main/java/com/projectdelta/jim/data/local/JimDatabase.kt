package com.projectdelta.jim.data.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.projectdelta.jim.BuildConfig
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
import com.projectdelta.jim.util.MockDebugData
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

        val MIGRATION_1_2 = object : androidx.room.migration.Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE INDEX IF NOT EXISTS `index_workout_table_sessionId` ON `workout_table` (`sessionId`)")
                database.execSQL("CREATE INDEX IF NOT EXISTS `index_workout_table_exerciseId` ON `workout_table` (`exerciseId`)")
                database.execSQL("CREATE INDEX IF NOT EXISTS `index_workout_set_table_workoutId` ON `workout_set_table` (`workoutId`)")
                database.execSQL("CREATE INDEX IF NOT EXISTS `index_workout_set_table_exerciseId` ON `workout_set_table` (`exerciseId`)")
            }
        }

        private fun buildDatabase(application: Application): JimDatabase {
            return Room.databaseBuilder(
                application,
                JimDatabase::class.java,
                NAME
            ).addMigrations(MIGRATION_1_2)
            .addCallback(object : Callback() {
                override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                    super.onDestructiveMigration(db)
                }

                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newSingleThreadExecutor().execute {
                        INSTANCE?.let {db ->
                            // Prepopulate data here if needed
                            if(BuildConfig.DEBUG){
                                MockDebugData.prePopulateDatabaseMock(
                                    application.resources,
                                    R.raw.exercises,
                                    { db.exerciseDao().insert(it) },
                                    { db.workoutSessionDao().insert(it) },
                                    { db.workoutDao().insert(it) },
                                    { db.workoutSetDao().insert(it) }
                                )
                            }
                            else {
                                val data = JSONUtils.getJsonFileAsClass(
                                    application.resources,
                                    R.raw.exercises,
                                    ExerciseWrapper::class.java
                                )
                                if (data.isPresent)
                                    db.exerciseDao().insertAll(data.get().exercises)
                            }
                        }
                    }
                }
            }).fallbackToDestructiveMigration()
                .build()
        }

    }
}
