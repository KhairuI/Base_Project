package com.example.baseproject.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.baseproject.BuildConfig
import com.example.baseproject.data.network.service.ApiService
import com.example.baseproject.di.ext.AppKeyQualifier
import com.example.baseproject.di.ext.DeviceIdQualifier
import com.example.baseproject.di.ext.LanguageQualifier
import com.example.baseproject.di.ext.PackageNameQualifier
import com.example.baseproject.di.ext.VersionNameQualifier
import com.example.baseproject.di.helper.ApiServicePostHelper
import com.example.baseproject.di.helper.ChuckerInterceptorHelper
import com.example.baseproject.di.helper.HeaderModel
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
    internal fun provideHeaderModel(
        @PackageNameQualifier packageName: String,
        @VersionNameQualifier versionName: String,
        @LanguageQualifier language: String,
        @DeviceIdQualifier deviceId: String
    ): HeaderModel = HeaderModel(
        packageName = packageName,
        versionName = versionName,
        language = language,
        deviceId = deviceId
    )

    @Provides
    @Singleton
    internal fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor =
        ChuckerInterceptorHelper(context).invoke()

    @Provides
    @Singleton
    internal fun provideApiServicePost(
        @ApplicationContext context: Context,
        chuckerInterceptor: ChuckerInterceptor,
        header: HeaderModel
    ): ApiService = ApiServicePostHelper(
        context = context, chuckerInterceptor = chuckerInterceptor, header = header
    ).invoke()
}