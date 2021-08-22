package com.mrezanasirloo.venues.map.view.venues

import java.text.DecimalFormat
import javax.inject.Inject


/**
 * Converts meter to kilometer
 */
class DistanceFormatter @Inject constructor() {
    private val formatter: DecimalFormat = DecimalFormat("#.#")

    fun format(distance: Int): String {
        return formatter.format(distance / 1000f) + " KM"
    }
}