package xyz.c3rberuss.databsemanager.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.c3rberuss.databsemanager.LocalDataSourceImpl
import xyz.c3rberuss.databsemanager.MoviesDatabase
import xyz.c3rberuss.movies.data.LocalDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            "movies_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providesLocalDataSourceImpl(db: MoviesDatabase): LocalDataSource {
        return LocalDataSourceImpl(db)
    }

}