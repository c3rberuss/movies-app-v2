package xyz.c3rberuss.movies.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.c3rberuss.domain.Movie
import xyz.c3rberuss.domain.Poster
import xyz.c3rberuss.domain.Resource
import xyz.c3rberuss.movies.usecases.*
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val fetchMovieUseCase: FetchMovieUseCase,
    private val addToFavoritesMoviesUseCase: AddToFavoritesMoviesUseCase,
    private val addToNoFavoritesMoviesUseCase: AddToNoFavoritesMoviesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val removeFromNoFavoritesUseCase: RemoveFromNoFavoritesUseCase
) : ViewModel() {

    val movie: MutableLiveData<Movie> = MutableLiveData()
    val status: MutableLiveData<Resource<Unit>> = MutableLiveData(Resource.Loading())
    val isFavorite: MutableLiveData<Boolean> = MutableLiveData(false)
    val isNoFavorite: MutableLiveData<Boolean> = MutableLiveData(false)

    fun fetchMovieDetail(movieId: Int, isFavoriteMovie: Boolean, isNoFavoriteMovie: Boolean) {
        fetchMovie(movieId)
        isFavorite.value = isFavoriteMovie
        isNoFavorite.value = isNoFavoriteMovie
    }

    private fun fetchMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = fetchMovieUseCase(movieId)
            viewModelScope.launch(Dispatchers.Main) {

                status.value = when (data) {
                    is Resource.Success -> {
                        movie.value = data.data
                        Resource.Success(Unit)
                    }
                    is Resource.Loading -> Resource.Loading()
                    else -> Resource.Failed(Exception())
                }

            }
        }
    }

    fun addToFavorites() {
        viewModelScope.launch(Dispatchers.IO) {

            val poster = Poster(
                id = movie.value?.id!!,
                image = movie.value?.poster!!,
                title = movie.value?.title!!,
                date = movie.value?.date!!,
                score = movie.value?.score!!,
                isFavorite = true,
                isNoFavorite = false
            )

            addToFavoritesMoviesUseCase(poster)
            viewModelScope.launch(Dispatchers.Main) {
                isFavorite.postValue(true)
                isNoFavorite.postValue(false)
            }
        }
    }

    fun addToNoFavorites() {
        viewModelScope.launch(Dispatchers.IO) {

            val poster = Poster(
                id = movie.value?.id!!,
                image = movie.value?.poster!!,
                title = movie.value?.title!!,
                date = movie.value?.date!!,
                score = movie.value?.score!!,
                isFavorite = false,
                isNoFavorite = true
            )

            addToNoFavoritesMoviesUseCase(poster)

            viewModelScope.launch(Dispatchers.Main) {
                isFavorite.postValue(false)
                isNoFavorite.postValue(true)
            }
        }
    }

    fun removeFromFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            removeFromFavoritesUseCase(movie.value?.id!!)

            viewModelScope.launch(Dispatchers.Main) {
                isNoFavorite.postValue(false)
                isFavorite.postValue(false)
            }
        }
    }

    fun removeFromNoFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            removeFromNoFavoritesUseCase(movie.value?.id!!)
            viewModelScope.launch(Dispatchers.Main) {
                isFavorite.postValue(false)
                isNoFavorite.postValue(false)
            }
        }
    }

}