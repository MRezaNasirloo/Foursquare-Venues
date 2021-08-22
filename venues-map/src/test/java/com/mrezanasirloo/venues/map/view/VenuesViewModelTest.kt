package com.mrezanasirloo.venues.map.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import com.mrezanasirloo.core.rx.AppSchedulers
import com.mrezanasirloo.navigation.venue.VenueArg
import com.mrezanasirloo.venues.map.datasource.*
import com.mrezanasirloo.venues.map.view.entity.Venue
import com.mrezanasirloo.venues.map.view.venues.DistanceFormatter
import com.mrezanasirloo.venues.map.view.venues.VenuesViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

class VenuesViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val response1 = VenuesResponse(
        Response(
            listOf(
                VenuesItem(id = "1", name = "foo", location = Location("", 35.691, 51.319, 0, "")),
                VenuesItem(id = "2", name = "foo", location = Location("", 35.691, 51.319, 0, "")),
                VenuesItem(id = "3", name = "foo", location = Location("", 35.691, 51.319, 0, "")),
            )
        )
    )


    @Test
    fun `should return venues after location change`() {
        val venuesRepository: VenuesRepository = mock {
            on { search(any(), any()) } doReturn Single.just(mock())
            on { venues(any()) } doReturn Observable.just(
                response1.response.venues
            )
        }

        val viewModel = VenuesViewModel(
            schedulers = AppSchedulers(Schedulers.trampoline(), Schedulers.trampoline()),
            repository = venuesRepository,
            formatter = DistanceFormatter(),
            compositeDisposable = CompositeDisposable()
        )

        val observer = mock<Observer<List<Venue>>>()
        viewModel.venues.observeForever(observer)

        viewModel.onLocationChange(mock(), mock())

        verify(observer, only()).onChanged(argThat {
            size == 3
        })
    }

    @Test
    fun `should open details venue page`() {
        val viewModel = VenuesViewModel(
            schedulers = mock(),
            repository = mock(),
            compositeDisposable = mock(),
            formatter = DistanceFormatter()
        )

        val observer = mock<Observer<VenueArg>>()
        viewModel.venueNavigation.observeForever(observer)

        viewModel.onVenueClicked(
            Venue(
                id = "1",
                latLng = LatLng(0.0, 0.0),
                name = "",
                address = "",
                distance = 123_321,
                city = ""
            )
        )

        verify(observer, only()).onChanged(argThat {
            id == "1" && distance == "123.3 KM"
        })
    }
}