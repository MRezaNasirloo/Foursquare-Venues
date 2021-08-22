package com.mrezanasirloo.venues.map.datasource

import android.annotation.SuppressLint
import android.os.Looper
import androidx.annotation.MainThread
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.subjects.MaybeSubject
import javax.inject.Inject

@SuppressLint("MissingPermission")
class LocationDataSource @Inject constructor(
    private val client: FusedLocationProviderClient
) {
    @MainThread
    fun lastLocation(): Maybe<LatLng> {
        return Maybe.defer {
            val subject = MaybeSubject.create<LatLng>()
            client.requestLocationUpdates(
                LocationRequest.create().apply {
                    numUpdates = 1
                    maxWaitTime = 15_000
                    priority = PRIORITY_HIGH_ACCURACY
                },
                object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        client.removeLocationUpdates(this)
                        subject.onSuccess(
                            LatLng(
                                result.lastLocation.latitude,
                                result.lastLocation.longitude
                            )
                        )
                        subject.onComplete()
                    }
                },
                Looper.getMainLooper()
            )
            return@defer subject
        }
    }
}