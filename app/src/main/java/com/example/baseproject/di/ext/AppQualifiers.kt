package com.example.baseproject.di.ext

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppKeyQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DeviceIdQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LanguageQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PackageNameQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class VersionNameQualifier