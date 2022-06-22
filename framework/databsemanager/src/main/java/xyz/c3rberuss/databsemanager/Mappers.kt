package xyz.c3rberuss.databsemanager

import xyz.c3rberuss.databsemanager.entities.PosterEntity
import xyz.c3rberuss.domain.Poster

fun PosterEntity.toDomain() = Poster(
    id = this.id,
    image = this.image,
    title = this.title,
    date = this.date,
    score = this.score,
    isFavorite = this.isFavorite,
    isNoFavorite = this.isNoFavorite
)


fun Poster.toEntity() : PosterEntity = PosterEntity(
    id = this.id,
    image = this.image,
    title = this.title,
    date = this.date,
    score = this.score,
    isFavorite = this.isFavorite,
    isNoFavorite = this.isNoFavorite,
)