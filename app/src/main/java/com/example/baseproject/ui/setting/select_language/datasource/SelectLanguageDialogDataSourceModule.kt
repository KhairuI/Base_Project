package com.example.baseproject.ui.setting.select_language.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SelectLanguageDialogDataSourceModule {

    @Provides
    @Singleton
    internal fun provideSelectLanguageDialogDataSource(selectLanguageDialogDataSourceImpl: SelectLanguageDialogDataSourceImpl): SelectLanguageDialogDataSource =
        selectLanguageDialogDataSourceImpl
}