package xyz.c3rberuss.databsemanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import xyz.c3rberuss.domain.Poster
import xyz.c3rberuss.domain.Resource
import xyz.c3rberuss.movies.data.LocalDataSource

class LocalDataSourceImpl constructor(
    private val db: MoviesDatabase,
) : LocalDataSource {
    override fun fetchFavoritesMovies(): LiveData<List<Poster>> {
        return Transformations.map(db.postersDao().fetchFavorites()) { list ->
            list.map { it.toDomain() }
        }
    }

    override fun fetchNoFavoritesMovies(): LiveData<List<Poster>> {
        return Transformations.map(db.postersDao().fetchNoFavorites()) { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun addToFavoritesMovies(movie: Poster): Resource<Unit> {
        return try {

            val poster = movie.toEntity().copy(
                isFavorite = true,
                isNoFavorite = false
            )

            db.postersDao().add(poster)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failed(e)
        }
    }

    override suspend fun addToNoFavoritesMovies(movie: Poster): Resource<Unit> {
        return try {

            val poster = movie.toEntity().copy(
                isFavorite = false,
                isNoFavorite = true
            )

            db.postersDao().add(poster)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failed(e)
        }
    }

    override suspend fun removeFromFavoritesMovies(movieId: Int): Resource<Unit> {

        return try {
            db.postersDao().remove(movieId)

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failed(e)
        }
    }

    override suspend fun removeFromNoFavoritesMovies(movieId: Int): Resource<Unit> {
        return try {
            db.postersDao().remove(movieId)

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failed(e)
        }
    }

    override suspend fun isFavoriteMovie(movieId: Int): Resource<Boolean> {
        return try {
            val result = db.postersDao().isFavorite(movieId) == 1
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Failed(e)
        }
    }

    override suspend fun isNoFavoriteMovie(movieId: Int): Resource<Boolean> {

        return try {
            val result = db.postersDao().isNoFavorite(movieId) == 1
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Failed(e)
        }
    }

}