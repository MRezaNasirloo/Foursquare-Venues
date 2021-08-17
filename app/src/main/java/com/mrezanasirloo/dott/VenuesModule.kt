package com.mrezanasirloo.dott

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
object VenuesModule {

    @Provides
    fun provideVenuesApi(retrofit: Retrofit): VenuesApi {
        return retrofit.create()
    }

}