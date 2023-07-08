package com.example.baseproject.ui.setting.select_language.delegate

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SelectLanguageDialogViewModelDelegateModule {

    @Provides
    @Singleton
    internal fun provideSelectLanguageDialogViewModelDelegate(
        selectLanguageDialogViewModelDelegateImpl: SelectLanguageDialogViewModelDelegateImpl
    ): SelectLanguageDialogViewModelDelegate =
        selectLanguageDialogViewModelDelegateImpl
}