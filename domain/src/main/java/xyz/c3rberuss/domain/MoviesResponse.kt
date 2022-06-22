package xyz.c3rberuss.domain

data class MoviesResponse(
        val page: Int,
        val results: List<Poster>,
        val totalPages: Int,
        val totalResults: Int,
)
