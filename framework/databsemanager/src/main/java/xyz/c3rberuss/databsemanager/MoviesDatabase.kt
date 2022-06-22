package xyz.c3rberuss.databsemanager

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.c3rberuss.databsemanager.daos.PosterDao
import xyz.c3rberuss.databsemanager.entities.PosterEntity

@Database(
    entities = [PosterEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun postersDao(): PosterDao
}