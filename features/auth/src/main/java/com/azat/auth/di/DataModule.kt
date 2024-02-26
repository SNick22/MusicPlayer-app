package com.azat.auth.di

import com.azat.auth.data.datasource.user.KtorUserDatasource
import com.azat.auth.data.datasource.user.UserDatasource
import com.azat.auth.data.repository.UserRepositoryImpl
import com.azat.auth.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface DataModule {

    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindUserDatasource(impl: KtorUserDatasource): UserDatasource
}
