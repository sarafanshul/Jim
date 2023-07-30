package com.projectdelta.jim.di

import com.projectdelta.jim.data.local.dao.ExerciseDao
import com.projectdelta.jim.data.local.dao.WorkoutSessionDao
import com.projectdelta.jim.data.repository.ExerciseRepository
import com.projectdelta.jim.data.repository.ExerciseRepositoryImpl
import com.projectdelta.jim.data.repository.WorkoutSessionRepository
import com.projectdelta.jim.data.repository.WorkoutSessionRepositoryImpl
import com.projectdelta.jim.di.qualifiers.IODispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideExerciseRepository(
        dao : ExerciseDao,
        @IODispatcher dispatcher: CoroutineDispatcher
    ): ExerciseRepository{
        return ExerciseRepositoryImpl(dao, dispatcher)
    }

    @Singleton
    @Provides
    fun provideWorkoutSessionRepository(
        dao : WorkoutSessionDao,
        @IODispatcher dispatcher: CoroutineDispatcher
    ): WorkoutSessionRepository{
        return WorkoutSessionRepositoryImpl(dao, dispatcher)
    }
}
