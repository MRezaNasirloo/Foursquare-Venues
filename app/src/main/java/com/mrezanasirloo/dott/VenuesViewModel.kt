package com.mrezanasirloo.dott

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.mrezanasirloo.dott.entity.Venue
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class VenuesViewModel @Inject constructor(
    private val repository: VenuesRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _venues = MutableLiveData<List<Venue>>()
    val venues: LiveData<List<Venue>> get() = _venues


    fun onLocationChange(latLng: LatLng, bounds: LatLngBounds) {
        compositeDisposable.clear()
        repository.search(latLng, bounds)
            .subscribeOn(Schedulers.computation())
            .map {
                it.map { item ->
                    Venue(
                        item.location.lat,
                        item.location.lng,
                        item.name
                    )
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _venues.value = it
                    Log.d("VENUES", "COUNT: ${it.size}")
                },
                onError = {
                    it.printStackTrace()
                }
            ).addTo(compositeDisposable)
    }
}