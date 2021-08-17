package com.mrezanasirloo.dott

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Reusable
    fun provideOkhttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val url = request.url.newBuilder()
                    .addQueryParameter("client_id", "OHKTMF5IQMLO3QAFGJXFTNGMAEUNVC4BNUTDENRC3BAXCJX1")
                    .addQueryParameter("client_secret", "KDAEXZEDKTTLXEHGAVQGCTDUI52N1SDZR2FF3UGV0YBFR1Z2")
                    .build()
                chain.proceed(
                    request.newBuilder()
                    .url(url)
                    .build()
                )
            }
            .addInterceptor { chain ->
                val request = chain.request()
                val url = request.url.newBuilder()
                    .addQueryParameter("v", "20210817")
                    .build()
                chain.proceed(
                    request.newBuilder()
                        .url(url)
                        .build()
                )
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(BODY))
            .build()
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

}