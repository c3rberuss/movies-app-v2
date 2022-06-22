package xyz.c3rberuss.databsemanager.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "poster")
data class PosterEntity(

    @PrimaryKey(autoGenerate = false)
    val id: Int = -1,

    val image: String,

    val title: String,

    val date: String,

    val score: Double,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,

    @ColumnInfo(name = "is_no_favorite")
    val isNoFavorite: Boolean = false,
)