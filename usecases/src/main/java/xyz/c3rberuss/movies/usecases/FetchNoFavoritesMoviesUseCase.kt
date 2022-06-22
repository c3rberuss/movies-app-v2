package xyz.c3rberuss.movies.usecases

import androidx.lifecycle.LiveData
import xyz.c3rberuss.domain.Poster
import xyz.c3rberuss.movies.data.MoviesRepository

class FetchNoFavoritesMoviesUseCase(private val repository: MoviesRepository) {
    operator fun invoke(): LiveData<List<Poster>> = repository.fetchNoFavoritesMovies()
}