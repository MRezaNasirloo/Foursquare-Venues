package com.mrezanasirloo.venues.map.view.venues

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.mrezanasirloo.core.livedata.SingleLiveEvent
import com.mrezanasirloo.core.rx.AppSchedulers
import com.mrezanasirloo.navigation.venue.VenueArg
import com.mrezanasirloo.venues.map.datasource.VenuesRepository
import com.mrezanasirloo.venues.map.view.entity.Venue
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VenuesViewModel @Inject constructor(
    private val schedulers: AppSchedulers,
    private val repository: VenuesRepository,
    private val formatter: DistanceFormatter,
    private val compositeDisposable: CompositeDisposable,
) : ViewModel() {

    private val _venues = MutableLiveData<List<Venue>>()
    val venues: LiveData<List<Venue>> get() = _venues

    private val _venueNavigation = SingleLiveEvent<VenueArg>()
    val venueNavigation: LiveData<VenueArg> get() = _venueNavigation

    fun onLocationChange(latLng: LatLng, bounds: LatLngBounds) {
        compositeDisposable.clear()
        repository.venues(bounds)
            .subscribeOn(schedulers.io)
            .map { items ->
                items.map { item ->
                    Venue(
                        id = item.id,
                        name = item.name,
                        city = item.location.city,
                        address = item.location.address,
                        distance = item.location.distance,
                        latLng = LatLng(item.location.lat, item.location.lng),
                    )
                }
            }
            .observeOn(schedulers.main)
            .subscribeBy(
                onNext = {
                    _venues.value = it
                },
                onError = {
                    Timber.log(Log.ERROR, it)
                }
            )

        repository.search(latLng, bounds)
            .ignoreElement()
            .subscribeOn(schedulers.io)
            .subscribeBy(
                onError = {
                    Timber.log(Log.ERROR, it)
                },
            )
            .addTo(compositeDisposable)
    }

    fun onVenueClicked(venue: Venue) {
        _venueNavigation.value = VenueArg(
            id = venue.id,
            name = venue.name,
            city = venue.city ?: "",
            address = venue.address ?: "",
            distance = formatter.format(venue.distance)
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}