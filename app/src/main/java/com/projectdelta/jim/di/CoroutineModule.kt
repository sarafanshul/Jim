package com.projectdelta.jim.di

import com.projectdelta.jim.di.qualifiers.DefaultDispatcher
import com.projectdelta.jim.di.qualifiers.IODispatcher
import com.projectdelta.jim.di.qualifiers.MainDispatcher
import com.projectdelta.jim.di.qualifiers.MainImmidiateDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher() : CoroutineDispatcher {
        return Dispatchers.Default
    }

    @IODispatcher
    @Provides
    fun provideIODispatcher() : CoroutineDispatcher {
        return Dispatchers.IO
    }

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    @MainImmidiateDispatcher
    @Provides
    fun provideMainImmidiateDispatcher() : CoroutineDispatcher {
        return Dispatchers.Main.immediate
    }

}
