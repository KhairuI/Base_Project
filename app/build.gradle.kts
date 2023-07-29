@file:Suppress("UnstableApiUsage")

import java.text.SimpleDateFormat
import java.util.Date

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin(module = "kapt")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("com.google.firebase.crashlytics")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("androidx.navigation.safeargs.kotlin")

}

android {
    namespace = "com.example.baseproject"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.baseproject"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val formattedDate = SimpleDateFormat("dd-MMM-yyyy h mm a").format(Date())
        val apkName =
            "BaseProject (com.example.baseproject) v${defaultConfig.versionName} - $formattedDate"
        setProperty("archivesBaseName", apkName)

        multiDexEnabled = true
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("../cred/baseproject.jks")
            storePassword = project.property("RELEASE_STORE_PASSWORD") as String
            keyPassword = project.property("RELEASE_KEY_PASSWORD") as String
            keyAlias = project.property("RELEASE_KEY_ALIAS") as String
        }
        create("release") {
            storeFile = file("../cred/baseproject.jks")
            storePassword = project.property("RELEASE_STORE_PASSWORD") as String
            keyPassword = project.property("RELEASE_KEY_PASSWORD") as String
            keyAlias = project.property("RELEASE_KEY_ALIAS") as String
        }
    }

    buildTypes {

        val domain = "\"https://dummy.com/\""
        val mapKey = "\"xhjgyuhguygxx\""
        defaultConfig {
            buildConfigField("String", "BUILD_TIME", "\"${System.currentTimeMillis()}\"")
            buildConfigField("String", "DOMAIN", domain)
            buildConfigField("String", "MAP_KEY", mapKey)
        }

        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            isDebuggable = true

            signingConfig = signingConfigs.getByName("debug")
            manifestPlaceholders["crashlyticsEnabled"] = false
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            isDebuggable = false

            signingConfig = signingConfigs.getByName("release")
            manifestPlaceholders["crashlyticsEnabled"] = true
        }
    }


    flavorDimensions += "version"

    productFlavors {
        // Data Type
        val stringType = "String"
        val booleanType = "boolean"
        val keyBaseUrl = "BASE_URL"
        val keyIsDevelopment = "IS_DEVELOPMENT"

        val betaUrl = "\"https://dummyjson.com/\""
        val baseUrl = "\"https://dummyjson.com/\""

        create("beta") {
            dimension = "version"
            buildConfigField(stringType, keyBaseUrl, betaUrl)
            buildConfigField(booleanType, keyIsDevelopment, "true")
            manifestPlaceholders["appLabel"] = "BaseProject beta"
        }
        create("production") {
            dimension = "version"
            buildConfigField(stringType, keyBaseUrl, baseUrl)
            buildConfigField(booleanType, keyIsDevelopment, "false")
            manifestPlaceholders["appLabel"] = "BaseProject"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        viewBinding = true
    }

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.fragment:fragment-ktx:1.6.0")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.google.android.play:core-ktx:1.8.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Multi Dex
    implementation("androidx.multidex:multidex:2.0.1")

    // Dependency Injection
    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-android-compiler:2.45")

    // In App Update
    implementation("com.google.android.play:core:1.10.3")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

    // Chuck-er
    debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")

    // Ste-tho
    implementation("com.facebook.stetho:stetho:1.6.0")
    implementation("com.facebook.stetho:stetho-okhttp3:1.6.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.15.1")

    // Room Database
    implementation("androidx.room:room-runtime:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")

    // Google
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.android.gms:play-services-auth:20.6.0")
    implementation("com.google.android.gms:play-services-auth-api-phone:18.0.1")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.maps.android:android-maps-utils:3.4.0")
    implementation("com.google.maps.android:maps-utils-ktx:3.4.0")

    // Dependency Injection
    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-android-compiler:2.45")

    // Network
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.7")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    // Parser
    implementation("com.google.code.gson:gson:2.10.1")

    // firebase
    implementation("com.google.firebase:firebase-crashlytics:18.4.0")
    implementation("com.google.firebase:firebase-analytics:21.3.0")
    implementation("com.google.firebase:firebase-messaging:23.2.0")
    implementation("com.google.firebase:firebase-inappmessaging-display:20.3.2")
    implementation("com.google.firebase:firebase-auth:22.1.0")
    implementation("com.google.firebase:firebase-firestore:24.7.0")
    implementation("com.google.firebase:firebase-storage:20.2.1")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.1")

    // Chuck-er
    debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.15.1")

    // Toasty
    implementation("com.github.GrenderG:Toasty:1.5.2")

    //navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")

    // Spin Kit
    implementation("com.github.ybq:Android-SpinKit:1.4.0")

    // Image Picker
    implementation("com.github.Drjacky:ImagePicker:2.3.22")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.15.1")

    // Fresco
    implementation("com.facebook.fresco:fresco") {
        version { strictly("2.6.0") }
    }

    implementation("com.github.chrisbanes:PhotoView:2.3.0")

}
kapt {
    correctErrorTypes = true
}
