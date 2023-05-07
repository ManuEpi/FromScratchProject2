package com.manuepi.fromscratchproject

import retrofit2.Response
import timber.log.Timber

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