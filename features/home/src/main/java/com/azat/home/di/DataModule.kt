package com.azat.home.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.azat.database.dao.MusicDao
import com.azat.database.entity.MusicEntity
import com.azat.home.data.datasource.music.remote.KtorMusicDatasource
import com.azat.home.data.datasource.music.remote.MusicRemoteMediator
import com.azat.home.data.datasource.music.remote.RemoteMusicDatasource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal class DataModule {

    @Provides
    fun provideMusicDatasource(impl: KtorMusicDatasource): RemoteMusicDatasource {
        return impl
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    fun provideMusicPager(
        remoteMediator: MusicRemoteMediator,
        musicDao: MusicDao
    ): Pager<Int, MusicEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = remoteMediator,
            pagingSourceFactory = {
                musicDao.pagingSource()
            }
        )
    }
}
