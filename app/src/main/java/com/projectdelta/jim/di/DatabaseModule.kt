package com.projectdelta.jim.di

import android.app.Application
import com.projectdelta.jim.data.local.ExerciseDao
import com.projectdelta.jim.data.local.JimDatabase
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
    fun provideJimDatabase(application: Application) : JimDatabase {
        return JimDatabase.getInstance(application)
    }

    @Singleton
    @Provides
    fun provideExerciseDao(database: JimDatabase) : ExerciseDao {
        return database.exerciseDao()
    }
}
