package com.example.baseproject.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.baseproject.BuildConfig
import com.example.baseproject.data.network.service.ApiServiceFCM
import com.example.baseproject.data.network.service.ApiServiceQuote
import com.example.baseproject.di.ext.DeviceIdQualifier
import com.example.baseproject.di.ext.LanguageQualifier
import com.example.baseproject.di.ext.PackageNameQualifier
import com.example.baseproject.di.ext.VersionNameQualifier
import com.example.baseproject.di.helper.ApiServiceFCMHelper
import com.example.baseproject.di.helper.ApiServiceQuoteHelper
import com.example.baseproject.di.helper.ChuckerInterceptorHelper
import com.example.baseproject.service.BaseProjectApp
import com.example.baseproject.utils.device.DeviceUtil
import com.example.baseproject.utils.helper.LocaleManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @PackageNameQualifier
    internal fun providePackageName(): String = BuildConfig.APPLICATION_ID

    @Provides
    @VersionNameQualifier
    internal fun provideVersionName(): String = BuildConfig.VERSION_NAME

    @Provides
    @LanguageQualifier
    internal fun provideLanguage(): String =
        BaseProjectApp.localeManager?.language ?: LocaleManager.LANGUAGE_ENGLISH

    @Provides
    @DeviceIdQualifier
    internal fun provideDeviceId(@ApplicationContext context: Context): String =
        DeviceUtil.getDeviceId(context)

    @Provides
    @Singleton
    internal fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor =
        ChuckerInterceptorHelper(context).invoke()

    @Provides
    @Singleton
    internal fun provideApiServiceQuote(chuckerInterceptor: ChuckerInterceptor): ApiServiceQuote =
        ApiServiceQuoteHelper(chuckerInterceptor).invoke()

    @Provides
    @Singleton
    internal fun provideApiServiceFCM(chuckerInterceptor: ChuckerInterceptor): ApiServiceFCM =
        ApiServiceFCMHelper(chuckerInterceptor).invoke()
}