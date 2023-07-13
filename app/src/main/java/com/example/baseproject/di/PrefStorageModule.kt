package com.example.baseproject.di

import com.example.baseproject.data.preferences.AppPreferenceHelper
import com.example.baseproject.data.preferences.PreferenceHelper
import com.example.baseproject.di.ext.Qualifier
import com.example.baseproject.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PrefStorageModule {

    @Provides
    @Singleton
    internal fun providePrefHelper(appPreferenceHelper: AppPreferenceHelper): PreferenceHelper =
        appPreferenceHelper

    @Provides
    @Named(Qualifier.prefName)
    internal fun providePrefName(): String = AppConstants.PREF_NAME
}