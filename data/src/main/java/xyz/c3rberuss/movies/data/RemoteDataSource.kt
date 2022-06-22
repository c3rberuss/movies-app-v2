package xyz.c3rberuss.movies.data

import xyz.c3rberuss.domain.Movie
import xyz.c3rberuss.domain.MoviesResponse
import xyz.c3rberuss.domain.Poster
import xyz.c3rberuss.domain.Resource

interface RemoteDataSource {
    suspend fun fetchPopularMovies(page: Int): Resource<MoviesResponse>
    suspend fun fetchMovie(movieId: Int): Resource<Movie>
}