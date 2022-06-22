package xyz.c3rberuss.movies.usecases

import xyz.c3rberuss.domain.Poster
import xyz.c3rberuss.domain.Resource
import xyz.c3rberuss.movies.data.MoviesRepository

class AddToFavoritesMoviesUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(movie: Poster): Resource<Unit> =
        repository.addToFavoritesMovies(movie)
}