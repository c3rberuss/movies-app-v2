package xyz.c3rberuss.movies.requestmanager

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbApi {

    @GET("movie/popular")
    suspend fun fetchPopularMovies(
        @Query("page") page: Int
    ): Response<PosterResponseSerializer>

    @GET("movie/{movie_id}")
    suspend fun fetchMovie(@Path("movie_id") movieId: Int): Response<MovieSerializer>

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "02e336007b8144f311debcece59f5eb4"
        const val IMAGES_URL = "https://www.themoviedb.org/t/p/w220_and_h330_face"
    }

}