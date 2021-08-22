package com.mrezanasirloo.core.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Adds API version date to each API call
 *
 * [more info][https://developer.foursquare.com/docs/places-api/versioning/]
 */
class ApiVersionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("v", "20210817")
            .build()
        return chain.proceed(
            request.newBuilder()
                .url(url)
                .build()
        )
    }
}