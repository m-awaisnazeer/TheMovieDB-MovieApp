package com.example.themoviedb.common.domain.model

data class Movie(
    val id:Int,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val overView:String,
    val isFavorite:Boolean
)
