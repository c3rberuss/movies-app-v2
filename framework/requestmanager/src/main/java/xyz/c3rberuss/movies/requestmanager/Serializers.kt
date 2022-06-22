package xyz.c3rberuss.movies.requestmanager

import com.squareup.moshi.Json
import java.util.*

data class MovieGenreSerializer(
    @Json(name = "name")
    val name: String
)

data class MovieSerializer(
    val id: Int,

    @Json(name = "poster_path")
    val poster: String,

    @Json(name = "backdrop_path")
    val background: String,
    val title: String,

    @Json(name = "release_date")
    val date: String,

    @Json(name = "vote_average")
    val score: Double,
    val overview: String,

    @Json(name = "tagline")
    val tag: String,

    val genres: List<MovieGenreSerializer>
)

data class PosterSerializer(
    var id: Int = -1,

    @Json(name = "poster_path")
    var image: String,

    var title: String,

    @Json(name = "release_date")
    var date: String,

    @Json(name = "vote_average")
    var score: Double,
)

data class PosterResponseSerializer(
    val page: Int,
    val results: List<PosterSerializer>,

    @Json(name = "total_pages")
    val totalPages: Int,

    @Json(name = "total_results")
    val totalResults: Int,
)