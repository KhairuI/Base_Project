package com.example.baseproject.utils.arch

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import kotlinx.coroutines.channels.SendChannel

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) =
    liveData.observe(this) { it?.let { t -> action(t) } }

fun <T> LifecycleOwner.observeEvent(
    liveData: LiveData<SingleEvent<T>>, action: (t: SingleEvent<T>) -> Unit
) = liveData.observe(this) { it?.let { t -> action(t) } }

fun <E> SendChannel<E>.tryOffer(element: E): Boolean = try {
    trySend(element).isSuccess
} catch (t: Throwable) {
    false // Ignore
}