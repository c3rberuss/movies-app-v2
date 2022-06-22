package xyz.c3rberuss.movies.requestmanager.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.c3rberuss.movies.data.RemoteDataSource
import xyz.c3rberuss.movies.requestmanager.RemoteDataSourceImpl
import xyz.c3rberuss.movies.requestmanager.TheMovieDbApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {

    @Provides
    @Singleton
    fun providesRemoteDataSourceImpl(
        api: TheMovieDbApi,
    ): RemoteDataSource {
        return RemoteDataSourceImpl(api)
    }
}