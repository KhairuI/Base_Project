package com.example.baseproject.ui.setting.delegate

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingViewModelDelegateModule {

    @Provides
    @Singleton
    internal fun provideSettingViewModelDelegate(settingViewModelDelegateImpl: SettingViewModelDelegateImpl): SettingViewModelDelegate =
        settingViewModelDelegateImpl
}