package xyz.c3rberuss.movies.usecases

import xyz.c3rberuss.domain.Movie
import xyz.c3rberuss.domain.Resource
import xyz.c3rberuss.movies.data.MoviesRepository

class FetchMovieUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(movieId: Int): Resource<Movie> = repository.fetchMovie(movieId)
}