package com.azat.home.data.datasource.music.remote

import com.azat.home.data.datasource.music.remote.dto.MusicResponse
import com.azat.network.Paths
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class KtorMusicDatasource @Inject constructor(
    private val client: HttpClient
): RemoteMusicDatasource {

    override suspend fun getAllMusic(limit: Int, offset: Long): Result<List<MusicResponse>> =
        runCatching {
            client.get(Paths.MUSIC) {
                parameter("limit", limit)
                parameter("offset", offset)
            }
                .body()
        }
}
