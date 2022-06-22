package xyz.c3rberuss.movies.data

import androidx.lifecycle.LiveData
import xyz.c3rberuss.domain.Poster
import xyz.c3rberuss.domain.Resource

interface LocalDataSource {
    fun fetchFavoritesMovies(): LiveData<List<Poster>>
    fun fetchNoFavoritesMovies(): LiveData<List<Poster>>
    suspend fun addToFavoritesMovies(movie: Poster): Resource<Unit>
    suspend fun addToNoFavoritesMovies(movie: Poster): Resource<Unit>
    suspend fun removeFromFavoritesMovies(movieId: Int): Resource<Unit>
    suspend fun removeFromNoFavoritesMovies(movieId: Int): Resource<Unit>
    suspend fun isFavoriteMovie(movieId: Int): Resource<Boolean>
    suspend fun isNoFavoriteMovie(movieId: Int): Resource<Boolean>
}