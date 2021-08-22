package com.mrezanasirloo.core.ext

sealed class Result<T> {
    class Success<T>(val value: T): Result<T>()
    class Error<T>(val throwable: Throwable): Result<T>()

    fun success(success: T.() -> Unit): Result<T> {
        if (this is Success<T>) success(value)
        return this
    }
    fun error(error: Throwable.() -> Unit): Result<T> {
        if (this is Error<T>) error(throwable)
        return this
    }
}