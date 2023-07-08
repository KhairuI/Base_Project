package com.example.baseproject.ui.setting.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SettingDataSourceModule {

    @Provides
    @Singleton
    internal fun provideSettingDataSource(settingDataSourceImpl: SettingDataSourceImpl): SettingDataSource =
        settingDataSourceImpl
}