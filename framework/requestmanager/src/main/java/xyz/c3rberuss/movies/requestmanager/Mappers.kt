package xyz.c3rberuss.movies.requestmanager

import xyz.c3rberuss.domain.Movie
import xyz.c3rberuss.domain.MovieGenre
import xyz.c3rberuss.domain.MoviesResponse
import xyz.c3rberuss.domain.Poster

fun PosterResponseSerializer.toDomain() = MoviesResponse(
    page = this.page,
    results = this.results.map { it.toDomain() },
    totalPages = this.totalPages,
    totalResults = this.totalResults
)

fun PosterSerializer.toDomain() = Poster(
    id = this.id,
    image = this.image,
    title = this.title,
    date = this.date,
    score = this.score,
    isFavorite = false,
    isNoFavorite = false
)

fun MovieSerializer.toDomain() = Movie(
    id = this.id,
    poster = this.poster,
    background = this.background,
    title = this.title,
    date = this.date,
    score = this.score,
    overview = this.overview,
    tag = this.tag,
    genres = this.genres.map { it.toDomain() }
)

fun MovieGenreSerializer.toDomain() = MovieGenre(name = this.name)