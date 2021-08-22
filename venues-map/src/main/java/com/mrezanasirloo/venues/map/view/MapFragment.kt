package com.mrezanasirloo.venues.map.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.SupportMapFragment
import com.mrezanasirloo.venues.map.R
import com.mrezanasirloo.venues.map.databinding.FragmentMapBinding
import com.mrezanasirloo.venues.map.view.user.UserLocationPlugin
import com.mrezanasirloo.venues.map.view.venues.VenuesPlugin
import dagger.hilt.android.AndroidEntryPoint

/**
 * Hosts a [SupportMapFragment].
 * For adding a feature extend [MapPlugin] interface.
 */
@AndroidEntryPoint
class MapFragment : Fragment() {

    private val plugins: List<MapPlugin> = listOf(
        UserLocationPlugin(this),
        VenuesPlugin(this),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMapBinding.inflate(layoutInflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fragment.getMapAsync { map ->
            plugins.forEach { it.init(map) }
        }
    }
}