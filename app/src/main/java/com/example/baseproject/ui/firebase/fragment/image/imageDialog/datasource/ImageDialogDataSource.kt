package com.example.baseproject.ui.firebase.fragment.image.imageDialog.datasource

import android.content.Context
import android.net.Uri
import com.example.baseproject.R
import com.example.baseproject.data.preferences.PreferenceHelper
import com.example.baseproject.di.ext.IoDispatcher
import com.example.baseproject.ui.base.datasource.NetworkHandlerDataSource
import com.example.baseproject.utils.FirebaseConstants
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.arch.UiText
import com.example.baseproject.utils.extension.isNetworkConnected
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ImageDialogDataSource {
    suspend fun image(name: String, uri: Uri): Flow<Result<String>>
    suspend fun saveUrl(name: String, url: String): Flow<Result<String>>
}

class ImageDialogDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val fireStore: FirebaseFirestore,
    private val storage: StorageReference,
    private val preferenceHelper: PreferenceHelper,
    private val networkHandlerDataSource: NetworkHandlerDataSource
) : ImageDialogDataSource {

    private fun hasInternetConnection(): Boolean = context.isNetworkConnected()

    override suspend fun image(name: String, uri: Uri): Flow<Result<String>> = flow {
        try {

            if (!hasInternetConnection()) {
                emit(Result.Error(UiText.StringResource(R.string.http_no_internet)))
                return@flow
            }

            emit(Result.Loading())

            storage.child(FirebaseConstants.IMAGE_LIST).child(preferenceHelper.getFirebaseUid())
                .child(name).putFile(uri).await().storage.downloadUrl.await().also {
                    emit(Result.Success(it.toString()))
                }

        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(networkHandlerDataSource.handleException(e)))
        }
    }.flowOn(ioDispatcher)

    override suspend fun saveUrl(name: String, url: String): Flow<Result<String>> = flow {
        try {

            if (!hasInternetConnection()) {
                emit(Result.Error(UiText.StringResource(R.string.http_no_internet)))
                return@flow
            }

            emit(Result.Loading())

            val map: MutableMap<String, String> = HashMap()
            map["name"] = name
            map["url"] = url

            fireStore.collection(FirebaseConstants.IMAGE_LIST)
                .document(preferenceHelper.getFirebaseUid())
                .collection(FirebaseConstants.IMAGES)
                .add(map).await().also {
                    emit(Result.Success("Insert Successfully"))
                }

        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(networkHandlerDataSource.handleException(e)))
        } finally {
            emit(Result.Loading(false))
        }
    }.flowOn(ioDispatcher)

    /*
    *    val map: MutableMap<String, String> = HashMap()
                    map["name"] = name
                    map["url"] = url.toString()

                    fireStore.collection(FirebaseConstants.IMAGE_LIST)
                        .document(preferenceHelper.getFirebaseUid())
                        .collection(FirebaseConstants.IMAGES)
                        .add(map)
                        * */


}