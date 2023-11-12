package com.example.myzulipapp.network

import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.concurrent.CancellationException
import javax.inject.Inject

class NetworkHandleService @Inject constructor() {

    suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): T {
        return when (val result = checkResponseStatus(call)) {
            is Result.Success -> result.data
            is Result.Error -> error(result.error)
        }
    }

    private suspend fun <T : Any> checkResponseStatus(call: suspend () -> Response<T>): Result<T> {
        var response: Response<T>? = null
        var body: T? = null
        var errorBody: ResponseBody? = null
        try {
            response = call.invoke()
            when {
                response.isSuccessful -> body = response.body()
                else -> errorBody = response.errorBody()
            }
        } catch (e: CancellationException) {
            throw e
        } catch (t: Throwable) {
            Log.d(TAG_ERROR, t.toString())
        }

        return when {
            body != null -> Result.Success(body)
            errorBody != null -> {
                val errorString = errorBody.charStream().use {
                    it.readText()
                }
                Result.Error(errorString)
            }

            else -> Result.Error("Unknown error: ${response?.raw()?.message}")
        }
    }

    companion object {
        private val TAG_ERROR = NetworkHandleService::class.java.simpleName
    }
}
