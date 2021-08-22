package com.mrezanasirloo.core.di

import com.mrezanasirloo.core.interceptor.ApiVersionInterceptor
import com.mrezanasirloo.core.interceptor.AuthInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Reusable
    fun provideOkhttp(interceptors: MutableSet<Interceptor>): OkHttpClient {
        return OkHttpClient.Builder().apply {
            interceptors.forEach { addInterceptor(it) }
        }.build()
    }

    @Provides
    @Reusable
    fun provideRetrofit(okhttp: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .client(okhttp)
            .baseUrl("https://api.foursquare.com/v2/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createSynchronous())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Reusable
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @IntoSet
    @Provides
    fun provideApiVersionInterceptor(): Interceptor {
        return ApiVersionInterceptor()
    }

    @IntoSet
    @Provides
    fun provideAuthInterceptor(): Interceptor {
        return AuthInterceptor()
    }

}