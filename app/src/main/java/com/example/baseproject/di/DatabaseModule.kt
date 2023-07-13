package com.example.baseproject.di

import android.content.Context
import androidx.room.Room
import com.example.baseproject.data.database.AppDatabase
import com.example.baseproject.data.database.repository.UserRepo
import com.example.baseproject.data.database.repository.UserRepository
import com.example.baseproject.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppConstants.DB_NAME)
            .fallbackToDestructiveMigration().allowMainThreadQueries().build()

    @Provides
    @Singleton
    internal fun provideUserRepoHelper(appDatabase: AppDatabase): UserRepo =
        UserRepository(appDatabase.userDao())
}