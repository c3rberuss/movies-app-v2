package xyz.c3rberuss.domain

import java.util.*

data class Movie(
        val id: Int,
        val poster: String,
        val background: String,
        val title: String,
        val date: String,
        val score: Double,
        val overview: String,
        val tag: String,
        val genres: List<MovieGenre>
)
