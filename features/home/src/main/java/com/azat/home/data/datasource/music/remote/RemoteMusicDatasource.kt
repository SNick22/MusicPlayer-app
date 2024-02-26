package com.azat.home.data.datasource.music.remote

import com.azat.home.data.datasource.music.remote.dto.MusicResponse

internal interface RemoteMusicDatasource {

    suspend fun getAllMusic(limit: Int, offset: Long): Result<List<MusicResponse>>
}
