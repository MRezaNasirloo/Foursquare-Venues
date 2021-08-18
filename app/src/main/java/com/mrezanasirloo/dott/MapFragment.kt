package com.mrezanasirloo.dott

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.measureTimeMillis

@AndroidEntryPoint
class MapFragment : SupportMapFragment() {

    private val viewModel: VenuesViewModel by viewModels()
    private val marker: BitmapDescriptor by lazy { createMarker() }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val poonak = LatLng(35.74947138937728, 51.32552328414975)
        with(googleMap) {
            addMarker(MarkerOptions().position(poonak).title("Marker in Poonak"))
            moveCamera(CameraUpdateFactory.newLatLngZoom(poonak, 15f))
            isMyLocationEnabled = true
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true

            setOnCameraIdleListener {
                val target = googleMap.cameraPosition.target
                viewModel.onLocationChange(target, projection.visibleRegion.latLngBounds)
            }

            viewModel.venues.observe(viewLifecycleOwner) { venues ->
                clear()
                venues.forEach { venue ->
                    addMarker(
                        MarkerOptions()
                            .position(LatLng(venue.lat, venue.lng))
                            .title(venue.name)
                    )
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMapAsync(callback)
    }

    private fun createMarker(): BitmapDescriptor {
        return ResourcesCompat.getDrawable(resources, R.drawable.ic_circle_4, null)!!.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(
                intrinsicWidth,
                intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}