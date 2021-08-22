package com.mrezanasirloo.venue.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mrezanasirloo.navigation.venue.VenueArg
import com.mrezanasirloo.venue.details.databinding.DetailsFragmentBinding

/**
 * A simple page for showing some info about a restaurant
 */
class DetailsFragment : BottomSheetDialogFragment() {

    private val venue: VenueArg by lazy {
        requireArguments().getParcelable("venue")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DetailsFragmentBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = DetailsFragmentBinding.bind(view)
        binding.title.text = venue.name
        binding.distance.text = venue.distance
        binding.subtitle.text = resources.getString(
            R.string.details_subtitle,
            venue.city,
            venue.address
        )
    }

}