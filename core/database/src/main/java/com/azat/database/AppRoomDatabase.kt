package com.azat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.azat.database.dao.MusicDao
import com.azat.database.entity.MusicEntity

@Database(
    entities = [MusicEntity::class],
    version = 1
)
abstract class AppRoomDatabase: RoomDatabase() {

    abstract val musicDao: MusicDao
}
