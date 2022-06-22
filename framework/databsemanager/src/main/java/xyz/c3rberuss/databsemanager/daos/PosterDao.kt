package xyz.c3rberuss.databsemanager.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import xyz.c3rberuss.databsemanager.entities.PosterEntity

@Dao
interface PosterDao {
    @Query("SELECT * FROM  poster WHERE is_favorite = 1 ORDER BY title ASC")
    fun fetchFavorites(): LiveData<List<PosterEntity>>

    @Query("SELECT * FROM  poster WHERE is_no_favorite = 1 ORDER BY title ASC")
    fun fetchNoFavorites(): LiveData<List<PosterEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(movie: PosterEntity)

    @Query("DELETE FROM poster WHERE id = :movieId")
    suspend fun remove(movieId: Int)

    @Query("SELECT COUNT(*) FROM poster WHERE id = :movieId AND is_favorite=1 LIMIT 1")
    suspend fun isFavorite(movieId: Int): Int

    @Query("SELECT COUNT(*) FROM poster WHERE id = :movieId AND is_no_favorite=1 LIMIT 1")
    suspend fun isNoFavorite(movieId: Int): Int
}