package com.example.baseproject.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    // get Firebase Auth Instance
    @Singleton
    @Provides
    fun getAuthInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }


    // get Firebase FireStore Auth Instance
    @Singleton
    @Provides
    fun provideFirebaseInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    // get Firebase Storage Instance
    @Singleton
    @Provides
    fun provideStorageReference(): StorageReference {
        return FirebaseStorage.getInstance().reference
    }
}