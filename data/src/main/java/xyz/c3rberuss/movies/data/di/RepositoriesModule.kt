package xyz.c3rberuss.movies.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.c3rberuss.movies.data.LocalDataSource
import xyz.c3rberuss.movies.data.MoviesRepository
import xyz.c3rberuss.movies.data.RemoteDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Provides
    @Singleton
    fun providesMoviesRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): MoviesRepository {
        return MoviesRepository(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
    }
}