package com.azat.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "music")
data class MusicEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val version: String?,
    val duration: Long,
    val author: String,
    val audio: String,
    val video: String?,
    val smallCover: String?,
    val largeCover: String?
)
