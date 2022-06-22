package xyz.c3rberuss.movies.popular

import androidx.paging.PagingSource
import androidx.paging.PagingState
import xyz.c3rberuss.domain.Poster
import xyz.c3rberuss.domain.Resource
import xyz.c3rberuss.movies.usecases.FetchPopularMoviesUseCase
import xyz.c3rberuss.movies.usecases.IsFavoriteMovieUseCase
import xyz.c3rberuss.movies.usecases.IsNoFavoriteMovieUseCase

class PopularMoviesPagingSource(
    private val fetchPopularMoviesUseCase: FetchPopularMoviesUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase,
    private val isNoFavoriteMovieUseCase: IsNoFavoriteMovieUseCase,
) : PagingSource<Int, Poster>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Poster> {
        return try {
            val nextPageNumber = params.key ?: 1

            val response = fetchPopularMoviesUseCase(
                page = nextPageNumber
            )

            if (response is Resource.Success) {

                val posters = response.data.results.map { poster ->
                    val isFavorite = when (val fav = isFavoriteMovieUseCase(poster.id)) {
                        is Resource.Success -> fav.data
                        else -> false
                    }

                    val noFavorite = when (val noFav = isNoFavoriteMovieUseCase(poster.id)) {
                        is Resource.Success -> noFav.data
                        else -> false
                    }

                    poster.copy(
                        isFavorite = isFavorite,
                        isNoFavorite = noFavorite,
                    )
                }

                LoadResult.Page(
                    data = posters,
                    prevKey = null,
                    nextKey = if (nextPageNumber < response.data.totalPages) response.data.page + 1 else null
                )
            } else {
                LoadResult.Error((response as Resource.Failed).exception)
            }


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Poster>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}