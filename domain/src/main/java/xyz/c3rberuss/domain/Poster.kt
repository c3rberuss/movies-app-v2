package xyz.c3rberuss.domain

data class Poster(
    val id: Int,
    val image: String,
    val title: String,
    val date: String,
    val score: Double,
    val isFavorite: Boolean,
    val isNoFavorite: Boolean
)
