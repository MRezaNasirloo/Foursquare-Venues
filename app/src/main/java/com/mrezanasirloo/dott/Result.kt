package com.mrezanasirloo.dott

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

sealed class Either<L> {
    class Success<L>(val value: L) : Either<L>()
    class Error<L>(val title: String, val message: String) : Either<L>()
    fun success(success: L.() -> Unit) {
        if (this is Success<L>) success(value)
    }
    fun error(error: (Error<L>).() -> Unit) {
        if (this is Error<L>) error(this)
    }
}