package com.mrezanasirloo.venues.map.view.user

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.mrezanasirloo.venues.map.databinding.FragmentMapBinding
import com.mrezanasirloo.venues.map.view.MapPlugin

@SuppressLint("MissingPermission")
class UserLocationPlugin(
    private val fragment: Fragment,
) : MapPlugin {

    private val context: Context by lazy { fragment.requireContext() }
    private val viewModel: UserLocationViewModel by fragment.viewModels()
    private val binding: FragmentMapBinding by lazy {
        FragmentMapBinding.bind(fragment.requireView())
    }

    private val launcher = fragment.registerForActivityResult(RequestPermission()) { granted ->
        viewModel.onPermissionResult(granted)
    }

    override fun init(map: GoogleMap) {
        viewModel.initialPosition.observe(fragment.viewLifecycleOwner) { latLan ->
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLan, 15f))
        }

        viewModel.userLocation.observe(fragment.viewLifecycleOwner) {
            map.isMyLocationEnabled = true
            map.uiSettings.isMyLocationButtonEnabled = false
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
        }

        viewModel.requestPermission.observe(fragment.viewLifecycleOwner) {
            launcher.launch(ACCESS_FINE_LOCATION)
        }

        viewModel.message.observe(fragment.viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        binding.myLocation.setOnClickListener {
            viewModel.onUserLocationRequested()
        }
    }
}