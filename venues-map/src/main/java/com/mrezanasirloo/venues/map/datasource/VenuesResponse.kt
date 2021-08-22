package com.mrezanasirloo.venues.map.datasource

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VenuesResponse(
    @Json(name = "response")
    val response: Response
)

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "address")
    val address: String?,
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "lng")
    val lng: Double,
    @Json(name = "distance")
    val distance: Int,
    @Json(name = "city")
    val city: String?,
)

@JsonClass(generateAdapter = true)
data class VenuesItem(
    @Json(name = "name")
    val name: String,
    @Json(name = "location")
    val location: Location,
    @Json(name = "id")
    val id: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VenuesItem

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

@JsonClass(generateAdapter = true)
data class Response(
    @Json(name = "venues")
    val venues: List<VenuesItem>
)
