package com.example.awaisahmadassignment.common.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
data class CachedMovie(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val isFavorite:Boolean
)