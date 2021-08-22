package com.mrezanasirloo.venues.map

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mrezanasirloo.venues.map.datasource.VenuesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
object VenuesModule {

    @Provides
    fun provideVenuesApi(retrofit: Retrofit): VenuesApi {
        return retrofit.create()
    }

    @Provides
    fun provideFusedClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

}