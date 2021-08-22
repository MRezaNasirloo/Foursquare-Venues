package com.mrezanasirloo.core.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Adds auth keys to each API call
 *
 * Usually, these keys should reside in a `local.properties` file or
 * on a CI/CD server and not committed to the source control
 */
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("client_id", "MUMI0TFFKXLZJ050DVP01L1RVMCTJ44OXNCA1KUJGALI211U")
            .addQueryParameter("client_secret", "S0NZY4FDWQ43F13CHBEU533G5SR1TIU4YXIRQP5CKVBQJU5C")
            .build()
        return chain.proceed(
            request.newBuilder()
                .url(url)
                .build()
        )
    }
}