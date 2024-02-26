package com.azat.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.azat.database.entity.MusicEntity

@Dao
interface MusicDao {

    @Upsert
    suspend fun upsertAll(musicList: List<MusicEntity>)

    @Query("SELECT * FROM music")
    fun pagingSource(): PagingSource<Int, MusicEntity>

    @Query("DELETE FROM music")
    suspend fun clearAll()
}
