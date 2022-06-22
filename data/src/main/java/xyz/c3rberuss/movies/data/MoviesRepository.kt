package xyz.c3rberuss.movies.data

import androidx.lifecycle.LiveData
import xyz.c3rberuss.domain.Movie
import xyz.c3rberuss.domain.MoviesResponse
import xyz.c3rberuss.domain.Poster
import xyz.c3rberuss.domain.Resource

class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun fetchPopularMovies(page: Int): Resource<MoviesResponse> =
        remoteDataSource.fetchPopularMovies(page)

    suspend fun fetchMovie(movieId: Int): Resource<Movie> = remoteDataSource.fetchMovie(movieId)

    fun fetchFavoritesMovies(): LiveData<List<Poster>> =
        localDataSource.fetchFavoritesMovies()

    fun fetchNoFavoritesMovies(): LiveData<List<Poster>> =
        localDataSource.fetchNoFavoritesMovies()

    suspend fun addToFavoritesMovies(movie: Poster): Resource<Unit> =
        localDataSource.addToFavoritesMovies(movie)

    suspend fun addToNoFavoritesMovies(movie: Poster): Resource<Unit> =
        localDataSource.addToNoFavoritesMovies(movie)

    suspend fun removeFromFavoritesMovies(movieId: Int): Resource<Unit> =
        localDataSource.removeFromFavoritesMovies(movieId)

    suspend fun removeFromNoFavoritesMovies(movieId: Int): Resource<Unit> =
        localDataSource.removeFromNoFavoritesMovies(movieId)

    suspend fun isFavoriteMovie(movieId: Int): Resource<Boolean> =
        localDataSource.isFavoriteMovie(movieId)

    suspend fun isNoFavoriteMovie(movieId: Int): Resource<Boolean> =
        localDataSource.isNoFavoriteMovie(movieId)

}