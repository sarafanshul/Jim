package com.projectdelta.jim.di

import com.projectdelta.jim.data.local.ExerciseDao
import com.projectdelta.jim.data.repository.ExerciseRepository
import com.projectdelta.jim.data.repository.ExerciseRepositoryImpl
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
}
