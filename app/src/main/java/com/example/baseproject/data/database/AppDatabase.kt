package com.example.baseproject.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.baseproject.data.database.repository.User
import com.example.baseproject.data.database.repository.UserDao
import com.example.baseproject.utils.AppConstants

@Database(
    entities = [
        (User::class)
    ],
    version = AppConstants.DB_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}