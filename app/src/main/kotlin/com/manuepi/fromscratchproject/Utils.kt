package com.manuepi.fromscratchproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}

suspend fun <T> MutableLiveData<T>.setState(block: suspend (T) -> T) {
    val oldValue = this.value
    if (oldValue != null) {
        val newValue = block(oldValue)
        withContext(Dispatchers.Main) {
            this@setState.value = newValue
        }
    } else {
        Timber.tag("ViewModel SetState").e(Throwable("Using SetState on Nullable Value"))
    }
}

sealed class NetworkResponse<T : Any> {
    class Success<T : Any>(val data: T) : NetworkResponse<T>()
    class Error<T : Any>(val message: String?) : NetworkResponse<T>()
}

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): NetworkResponse<T> {
    return try {
        val result = call.invoke()
        if (result.isSuccessful) {
            NetworkResponse.Success(data = result.body()!!)
        } else
            NetworkResponse.Error(message = "Une erreur est survenue")
    } catch (t: Throwable) {
        Timber.e(t)
        NetworkResponse.Error(message = "Une erreur est survenue")
    }
}