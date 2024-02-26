package com.azat.home.data.datasource.music.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.azat.database.AppRoomDatabase
import com.azat.database.entity.MusicEntity
import com.azat.home.data.mappers.toMusicEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
internal class MusicRemoteMediator @Inject constructor(
    private val database: AppRoomDatabase,
    private val remoteMusicDatasource: RemoteMusicDatasource
): RemoteMediator<Int, MusicEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MusicEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.pages.size * state.config.pageSize
                }
            }

            val remoteMusics = remoteMusicDatasource.getAllMusic(
                limit = state.config.pageSize,
                offset = loadKey.toLong()
            ).getOrThrow()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.musicDao.clearAll()
                }
                val localMusics = remoteMusics.map { it.toMusicEntity() }
                database.musicDao.upsertAll(localMusics)
            }

            MediatorResult.Success(endOfPaginationReached = remoteMusics.isEmpty())
        } catch (e: Throwable) {
            throw e
        }
    }
}
