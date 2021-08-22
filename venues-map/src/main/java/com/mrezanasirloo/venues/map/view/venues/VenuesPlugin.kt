package com.mrezanasirloo.venues.map.view.venues

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.mrezanasirloo.venues.map.view.MapPlugin
import com.mrezanasirloo.venues.map.view.entity.Venue

/**
 * Shows venues around the current user location
 *
 * to improve performance, it utilizes the google map Clustering library
 */
class VenuesPlugin(
    private val fragment: Fragment,
) : MapPlugin {

    private val viewModel: VenuesViewModel by fragment.viewModels()
    private var currentCameraZoom: Float = 1f

    override fun init(map: GoogleMap) {
        with(map) {
            uiSettings.isZoomControlsEnabled = true
            val clusterManager = ClusterManager<Venue>(fragment.context, this)
            val maxZoom = maxZoomLevel
            clusterManager.renderer =
                object : DefaultClusterRenderer<Venue>(fragment.context, this, clusterManager) {
                    override fun shouldRenderAsCluster(cluster: Cluster<Venue>): Boolean {
                        //Don't cluster on maximum zoom level
                        return currentCameraZoom != maxZoom && super.shouldRenderAsCluster(cluster)
                    }
                }

            clusterManager.setOnClusterClickListener {
                val bounds = LatLngBounds.builder().apply {
                    it.items.forEach { venue ->
                        include(venue.position)
                    }
                }.build()
                animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150))
                true
            }

            clusterManager.setOnClusterItemClickListener {
                viewModel.onVenueClicked(it)
                true
            }

            setOnCameraIdleListener {
                currentCameraZoom = cameraPosition.zoom
                clusterManager.onCameraIdle()
                viewModel.onLocationChange(
                    cameraPosition.target,
                    projection.visibleRegion.latLngBounds
                )
            }

            viewModel.venues.observe(fragment.viewLifecycleOwner) { venues ->
                clusterManager.clearItems()
                clusterManager.addItems(venues)
                clusterManager.cluster()
            }

            viewModel.venueNavigation.observe(fragment.viewLifecycleOwner) { arg ->

            }
        }
    }
}