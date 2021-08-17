package com.mrezanasirloo.dott

import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class VenuesRepository @Inject constructor(
    private val api: VenuesApi
) {

    fun search(lat: Double, lng: Double): Single<VenuesResponse> {
        return api.search(ll = "$lat,$lng", categoryId = "4d4b7105d754a06374d81259")
    }
}