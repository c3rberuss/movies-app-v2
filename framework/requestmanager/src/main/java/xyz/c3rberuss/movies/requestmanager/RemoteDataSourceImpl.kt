package xyz.c3rberuss.movies.requestmanager

import timber.log.Timber
import xyz.c3rberuss.domain.Movie
import xyz.c3rberuss.domain.MoviesResponse
import xyz.c3rberuss.domain.Resource
import xyz.c3rberuss.movies.data.RemoteDataSource

class RemoteDataSourceImpl constructor(private val api: TheMovieDbApi) : RemoteDataSource {

    override suspend fun fetchPopularMovies(page: Int): Resource<MoviesResponse> {
        return try {

            val response = api.fetchPopularMovies(page)

            if (response.code() == 200) {
                val data = response.body()!!.toDomain()

                Resource.Success(data)
            } else {

                Timber.tag("REMOTE DATA SOURCE").e(response.message())

                Resource.Failed(Exception(response.message()))
            }

        } catch (e: Exception) {

            Timber.tag("REMOTE DATA SOURCE").e(e.message!!)

            Resource.Failed(e)
        }
    }

    override suspend fun fetchMovie(movieId: Int): Resource<Movie> {
        return try {
            val response = api.fetchMovie(movieId)

            if (response.code() == 200) {
                val movie = response.body()!!.toDomain()
                Resource.Success(movie)
            } else {
                Resource.Failed(Exception(response.message()))
            }

        } catch (e: Exception) {
            Resource.Failed(e)
        }
    }
}