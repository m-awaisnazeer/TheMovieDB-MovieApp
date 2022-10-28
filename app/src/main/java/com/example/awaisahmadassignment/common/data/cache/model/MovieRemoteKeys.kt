package com.example.awaisahmadassignment.common.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="MovieRemoteKeys" )
data class MovieRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val prevPage:Int?,
    val nextPage:Int?
)