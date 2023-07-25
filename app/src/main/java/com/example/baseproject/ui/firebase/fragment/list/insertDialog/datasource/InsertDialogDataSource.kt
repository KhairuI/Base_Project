package com.example.baseproject.ui.firebase.fragment.list.insertDialog.datasource

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.baseproject.R
import com.example.baseproject.data.preferences.PreferenceHelper
import com.example.baseproject.di.ext.IoDispatcher
import com.example.baseproject.ui.base.datasource.NetworkHandlerDataSource
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

interface InsertDialogDataSource {
    fun insert(text: String): MutableLiveData<String>
    fun insert2(text: String): Flow<Result<String>>
}

class InsertDialogDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val fireStore: FirebaseFirestore,
    private val preferenceHelper: PreferenceHelper,
    private val networkHandlerDataSource: NetworkHandlerDataSource
) : InsertDialogDataSource {

    private fun hasInternetConnection(): Boolean = context.isNetworkConnected()

    override fun insert(text: String): MutableLiveData<String> {
        val result = MutableLiveData<String>()

        //  result.value = Result.Loading()
        Log.d("xxx", "insert: enter try")

        if (!hasInternetConnection()) {
            result.value = context.getString(R.string.text_missing)
            return result
        }

        val map: MutableMap<String, String> = HashMap()
        map["name"] = text

        fireStore.collection(FirebaseConstants.TEXT_LIST)
            .document(preferenceHelper.getFirebaseUid()).collection(FirebaseConstants.LIST)
            .add(map).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("xxx", "insert: task success")
                    result.value = "Insert Successfully"
                } else {
                    Log.d("xxx", "insert: task fail")
                    result.value = "Insert Failed"
                }
            }
        return result
    }

    override fun insert2(text: String): Flow<Result<String>> = flow {
        try {

            emit(Result.Loading())
            if (!hasInternetConnection()) {
                emit(Result.Error(UiText.StringResource(R.string.http_no_internet)))
                return@flow
            }

            val map: MutableMap<String, String> = HashMap()
            map["name"] = text

            val task = fireStore.collection(FirebaseConstants.TEXT_LIST)
                .document(preferenceHelper.getFirebaseUid()).collection(FirebaseConstants.LIST)
                .add(map).await()
            if (task.get().isSuccessful) {
                emit(Result.Success("Insert Successfully"))
            } else {
                emit(Result.Error(UiText.DynamicString(task.get().exception.toString())))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(networkHandlerDataSource.handleException(e)))
        } finally {
            emit(Result.Loading(false))
        }
    }.flowOn(ioDispatcher)

}