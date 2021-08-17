package com.mrezanasirloo.dott

import retrofit2.HttpException
import retrofit2.Response

fun <T> Response<T>.toResult(): Result<T> {
    return try {
        if (isSuccessful) {
            Result.Success(requireNotNull(body()))
        } else {
            Result.Error(HttpException(this))
        }
    } catch (e: Exception) {
        Result.Error(e)
    }
}
