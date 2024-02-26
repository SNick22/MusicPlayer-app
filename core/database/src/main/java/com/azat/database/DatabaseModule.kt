package com.azat.database

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.azat.database.dao.MusicDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideTokenPrefs(sharedPreferences: SharedPreferences): TokenPrefs {
        return TokenPrefsImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppRoomDatabase {
        return Room.databaseBuilder(
            context = appContext,
            klass = AppRoomDatabase::class.java,
            name = DB_NAME
        )
            .build()
    }

    @Provides
    fun provideMusicDao(database: AppRoomDatabase): MusicDao {
        return database.musicDao
    }

    companion object {
        private const val PREFERENCES_NAME = "music_player_preferences"
        private const val DB_NAME = "music_player_database"
    }
}
