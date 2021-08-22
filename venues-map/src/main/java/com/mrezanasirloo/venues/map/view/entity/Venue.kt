package com.mrezanasirloo.venues.map.view.entity

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class Venue(
    val id: String,
    val latLng: LatLng,
    val name: String,
    val address: String?,
    val distance: Int,
    val city: String?,
) : ClusterItem, Parcelable {

    override fun getPosition(): LatLng = latLng
    override fun getTitle(): String = name
    override fun getSnippet(): String? = null
}
