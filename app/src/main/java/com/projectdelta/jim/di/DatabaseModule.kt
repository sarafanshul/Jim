package com.projectdelta.jim.di

import android.app.Application
import com.projectdelta.jim.data.local.dao.ExerciseDao
import com.projectdelta.jim.data.local.JimDatabase
import com.projectdelta.jim.data.local.dao.WorkoutDao
import com.projectdelta.jim.data.local.dao.WorkoutSessionDao
import com.projectdelta.jim.data.local.dao.WorkoutSetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideJimDatabase(application: Application): JimDatabase {
        return JimDatabase.getInstance(application)
    }

    @Singleton
    @Provides
    fun provideExerciseDao(database: JimDatabase): ExerciseDao {
        return database.exerciseDao()
    }

    @Singleton
    @Provides
    fun provideWorkoutSessionDao(database: JimDatabase): WorkoutSessionDao {
        return database.workoutSessionDao()
    }

    @Singleton
    @Provides
    fun provideWorkoutDao(database: JimDatabase): WorkoutDao {
        return database.workoutDao()
    }

    @Singleton
    @Provides
    fun provideWorkoutSetDao(database: JimDatabase): WorkoutSetDao {
        return database.workoutSetDao()
    }

}
