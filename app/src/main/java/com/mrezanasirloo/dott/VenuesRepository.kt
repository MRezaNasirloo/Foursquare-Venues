package com.mrezanasirloo.dott

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.ktx.utils.sphericalDistance
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class VenuesRepository @Inject constructor(
    private val api: VenuesApi,
    private val venuesStore: VenuesStore,
) {
    private val disposable = CompositeDisposable()


    fun search(latLng: LatLng, bounds: LatLngBounds): Observable<List<VenuesItem>> {
        disposable.clear()
        val diameter = bounds.northeast.sphericalDistance(bounds.southwest)

        api.search(
            ll = "${latLng.latitude},${latLng.longitude}",
            categoryId = "4bf58dd8d48988d1c4941735",
//            categoryId = "4d4b7105d754a06374d81259",//restaurant
            radius = diameter.toLong() / 2,
        ).subscribeOn(Schedulers.io())
            .subscribeBy { venuesStore.add(it.response.venues) }
            .addTo(disposable)

        return venuesStore.venues()
            .map {
                it.filter { item ->
                    bounds.contains(LatLng(item.location.lat, item.location.lng))
                }
            }.distinctUntilChanged()
    }
}