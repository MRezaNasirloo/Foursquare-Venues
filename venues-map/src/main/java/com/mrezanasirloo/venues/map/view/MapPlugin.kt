package com.mrezanasirloo.venues.map.view

import com.google.android.gms.maps.GoogleMap

/**
 * This interface helps us to develop map features independently
 */
interface MapPlugin {
    /**
     * Called when map is ready
     */
    fun init(map: GoogleMap)
}