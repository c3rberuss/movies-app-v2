package xyz.c3rberuss.movies.usecases

import xyz.c3rberuss.domain.MoviesResponse
import xyz.c3rberuss.domain.Poster
import xyz.c3rberuss.domain.Resource
import xyz.c3rberuss.movies.data.MoviesRepository

class FetchPopularMoviesUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(page: Int): Resource<MoviesResponse> =
        repository.fetchPopularMovies(page)
}