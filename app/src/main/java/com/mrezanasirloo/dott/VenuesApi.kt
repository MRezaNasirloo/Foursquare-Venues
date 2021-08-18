package com.mrezanasirloo.dott

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface VenuesApi {

    /**
     * Read more on [Foursquare Venues Api][https://developer.foursquare.com/docs/api-reference/venues/search/]
     *
     * @param ll Lat and Lng separated with comma ex: "40.74224,-73.99386"
     */
    @GET("venues/search")
    fun search(
        @Query("ll") ll: String,
        @Query("query") query: String = "",
        @Query("categoryId") categoryId: String = "",
        @Query("radius") radius: Long = 500,
        @Query("limit") limit: Int = 50,
    ): Single<VenuesResponse>
}