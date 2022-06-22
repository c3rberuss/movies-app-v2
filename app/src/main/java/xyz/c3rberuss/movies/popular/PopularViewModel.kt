package xyz.c3rberuss.movies.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import xyz.c3rberuss.domain.Poster
import xyz.c3rberuss.movies.usecases.FetchPopularMoviesUseCase
import xyz.c3rberuss.movies.usecases.IsFavoriteMovieUseCase
import xyz.c3rberuss.movies.usecases.IsNoFavoriteMovieUseCase
import javax.inject.Inject


@HiltViewModel
class PopularViewModel @Inject constructor(
    private val fetchPopularMovies: FetchPopularMoviesUseCase,
    private val isFavoriteMovie: IsFavoriteMovieUseCase,
    private val isNoFavoriteMovie: IsNoFavoriteMovieUseCase
) : ViewModel() {

    val popularMovies: LiveData<PagingData<Poster>>
        get() = Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = true),
            initialKey = 1,
        ) {
            PopularMoviesPagingSource(
                fetchPopularMoviesUseCase = fetchPopularMovies,
                isFavoriteMovieUseCase = isFavoriteMovie,
                isNoFavoriteMovieUseCase = isNoFavoriteMovie,
            )
        }.flow.flowOn(Dispatchers.IO).cachedIn(viewModelScope).asLiveData()
}