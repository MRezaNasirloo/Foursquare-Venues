package com.mrezanasirloo.dott

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class VenuesViewModel @Inject constructor(
    private val repository: VenuesRepository
) : ViewModel() {


    fun onLocationChange(lat: Double, lng: Double) {
        repository.search(lat, lng)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    println(it.response.venues.size)
                },
                onError = {
                    it.printStackTrace()
                }
            )
    }
}