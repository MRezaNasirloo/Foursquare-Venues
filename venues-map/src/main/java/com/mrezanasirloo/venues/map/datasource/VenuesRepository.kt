package com.mrezanasirloo.venues.map.datasource

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.ktx.utils.sphericalDistance
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * A cached backed repository
 */
class VenuesRepository @Inject constructor(
    private val api: VenuesApi,
    private val venuesStore: VenuesStore,
) {


    /**
     * Search for venues that are located around [latLng],
     * These venues are inside or near this [bounds]
     *
     * @param latLng the search central point
     * @param bounds this is used to calculate the radius of the visible area
     */
    fun search(latLng: LatLng, bounds: LatLngBounds): Single<VenuesResponse> {
        val diameter = bounds.northeast.sphericalDistance(bounds.southwest)
        return api.search(
            ll = "${latLng.latitude},${latLng.longitude}",
            categoryId = "4bf58dd8d48988d1c4941735",// Restaurants categoryId
            radius = diameter.toLong() / 2,
        ).doOnSuccess {
            venuesStore.add(it.response.venues)
        }
    }

    /**
     * Returns the venues that are located withing the supplied [bounds]
     */
    fun venues(bounds: LatLngBounds): Observable<List<VenuesItem>> {
        return venuesStore.venues().map {
            it.filter { item ->
                bounds.contains(LatLng(item.location.lat, item.location.lng))
            }
        }.distinctUntilChanged()
    }
}