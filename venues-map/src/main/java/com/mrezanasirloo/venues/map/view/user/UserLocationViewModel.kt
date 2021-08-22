package com.mrezanasirloo.venues.map.view.user

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.mrezanasirloo.core.livedata.SingleLiveEvent
import com.mrezanasirloo.venues.map.R
import com.mrezanasirloo.venues.map.datasource.LocationDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserLocationViewModel @Inject constructor(
    application: Application,
    private val userLocationDataSource: LocationDataSource,
    private val compositeDisposable: CompositeDisposable
) : AndroidViewModel(application) {

    private val _userLocation = SingleLiveEvent<LatLng>()
    val userLocation: LiveData<LatLng> get() = _userLocation

    private val _requestPermission = SingleLiveEvent<Unit>()
    val requestPermission: LiveData<Unit> get() = _requestPermission

    private val _message = SingleLiveEvent<String>()
    val message: LiveData<String> get() = _message

    private val _initialMapPosition = SingleLiveEvent<LatLng>().apply {
        value = LatLng(52.3676, 4.9041)
    }
    val initialPosition: LiveData<LatLng> get() = _initialMapPosition

    private fun fetchUserLocation() {
        compositeDisposable.clear()
        userLocationDataSource.lastLocation()
            .subscribeBy(
                onSuccess = {
                    _userLocation.value = it
                },
                onError = {
                    Timber.log(Log.ERROR, it)
                }
            ).addTo(compositeDisposable)
    }

    fun onUserLocationRequested() {
        if (hasPermission()) {
            fetchUserLocation()
        } else {
            _requestPermission.value = Unit
        }
    }

    fun onPermissionResult(granted: Boolean) {
        if (granted) {
            fetchUserLocation()
        } else {
            _message.value = getApplication<Application>()
                .getString(R.string.location_permission_denied)
        }
    }

    private fun hasPermission() = ContextCompat.checkSelfPermission(
        getApplication(), Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    override fun onCleared() {
        compositeDisposable.dispose()
    }
}