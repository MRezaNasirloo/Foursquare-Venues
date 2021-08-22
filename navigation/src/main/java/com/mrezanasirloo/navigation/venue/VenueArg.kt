package com.mrezanasirloo.navigation.venue

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VenueArg(
    val id: String,
    val name: String,
    val address: String,
    val distance: String,
    val city: String,
) : Parcelable
