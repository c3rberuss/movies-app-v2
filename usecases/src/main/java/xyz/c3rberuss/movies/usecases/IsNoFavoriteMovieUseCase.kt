package xyz.c3rberuss.movies.usecases

import xyz.c3rberuss.domain.Resource
import xyz.c3rberuss.movies.data.MoviesRepository

class IsNoFavoriteMovieUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(movieId: Int): Resource<Boolean> =
        repository.isNoFavoriteMovie(movieId)
}