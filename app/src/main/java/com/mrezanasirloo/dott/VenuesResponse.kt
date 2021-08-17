package com.mrezanasirloo.dott

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VenuesResponse(
	@Json(name="meta")
	val meta: Meta,
	@Json(name="response")
	val response: Response
)
@JsonClass(generateAdapter = true)
data class Meta(
	@Json(name="code")
	val code: Int,
	@Json(name="requestId")
	val requestId: String
)
@JsonClass(generateAdapter = true)
data class VenuePage(
	@Json(name="id")
	val id: String
)
@JsonClass(generateAdapter = true)
data class LabeledLatLngsItem(
	@Json(name="lng")
	val lng: Double,
	@Json(name="label")
	val label: String,
	@Json(name="lat")
	val lat: Double
)
@JsonClass(generateAdapter = true)
data class CategoriesItem(
	@Json(name="pluralName")
	val pluralName: String,
	@Json(name="name")
	val name: String,
	@Json(name="icon")
	val icon: Icon,
	@Json(name="id")
	val id: String,
	@Json(name="shortName")
	val shortName: String,
	@Json(name="primary")
	val primary: Boolean
)
@JsonClass(generateAdapter = true)
data class Location(
	@Json(name="cc")
	val cc: String,
	@Json(name="country")
	val country: String,
	@Json(name="address")
	val address: String?,
	@Json(name="labeledLatLngs")
	val labeledLatLngs: List<LabeledLatLngsItem>,
	@Json(name="lat")
	val lat: Double,
	@Json(name="lng")
	val lng: Double,
	@Json(name="distance")
	val distance: Int,
	@Json(name="formattedAddress")
	val formattedAddress: List<String>,
	@Json(name="city")
	val city: String?,
	@Json(name="postalCode")
	val postalCode: String?,
	@Json(name="state")
	val state: String?,
	@Json(name="crossStreet")
	val crossStreet: String?
)
@JsonClass(generateAdapter = true)
data class VenuesItem(
	@Json(name="venuePage")
	val venuePage: VenuePage?,
	@Json(name="name")
	val name: String,
	@Json(name="location")
	val location: Location,
	@Json(name="id")
	val id: String,
	@Json(name="categories")
	val categories: List<CategoriesItem>
)
@JsonClass(generateAdapter = true)
data class Response(
	@Json(name="venues")
	val venues: List<VenuesItem>
)
@JsonClass(generateAdapter = true)
data class Icon(
	@Json(name="prefix")
	val prefix: String,
	@Json(name="suffix")
	val suffix: String
)
