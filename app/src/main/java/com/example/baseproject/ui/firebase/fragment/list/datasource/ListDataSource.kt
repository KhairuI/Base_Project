package com.example.baseproject.ui.firebase.fragment.list.datasource

import android.content.Context
import com.example.baseproject.R
import com.example.baseproject.data.preferences.PreferenceHelper
import com.example.baseproject.di.ext.IoDispatcher
import com.example.baseproject.ui.base.datasource.NetworkHandlerDataSource
import com.example.baseproject.ui.firebase.fragment.list.model.ModelList
import com.example.baseproject.utils.FirebaseConstants
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.arch.UiText
import com.example.baseproject.utils.extension.isNetworkConnected
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ListDataSource {
    suspend fun getList(): Flow<Result<List<ModelList>>>
}

class ListDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val fireStore: FirebaseFirestore,
    private val preferenceHelper: PreferenceHelper,
    private val networkHandlerDataSource: NetworkHandlerDataSource
) : ListDataSource {

    private fun hasInternetConnection(): Boolean = context.isNetworkConnected()

    override suspend fun getList(): Flow<Result<List<ModelList>>> = flow {
        try {

            if (!hasInternetConnection()) {
                emit(Result.Error(UiText.StringResource(R.string.http_no_internet)))
                return@flow
            }

            emit(Result.Loading())

            fireStore.collection(FirebaseConstants.TEXT_LIST)
                .document(preferenceHelper.getFirebaseUid()).collection(FirebaseConstants.LIST)
                .get().await().also { snapshot ->
                    emit(Result.Success(snapshot.toObjects(ModelList::class.java)))
                }


        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(networkHandlerDataSource.handleException(e)))
        } finally {
            emit(Result.Loading(false))
        }
    }.flowOn(ioDispatcher)


}