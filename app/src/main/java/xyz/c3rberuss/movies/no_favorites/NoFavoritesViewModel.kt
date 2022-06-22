package xyz.c3rberuss.movies.no_favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.c3rberuss.domain.Poster
import xyz.c3rberuss.movies.usecases.FetchNoFavoritesMoviesUseCase
import javax.inject.Inject


@HiltViewModel
class NoFavoritesViewModel @Inject constructor(
    private val fetchNoFavoritesMovies: FetchNoFavoritesMoviesUseCase
) : ViewModel() {
    val noFavoritesMovies: LiveData<List<Poster>> get() = fetchNoFavoritesMovies()
}