package xyz.c3rberuss.movies.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.c3rberuss.domain.Poster
import xyz.c3rberuss.movies.usecases.FetchFavoritesMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val fetchFavoritesMovies: FetchFavoritesMoviesUseCase
) : ViewModel() {

    val favoritesMovies: LiveData<List<Poster>> get() = fetchFavoritesMovies()

}