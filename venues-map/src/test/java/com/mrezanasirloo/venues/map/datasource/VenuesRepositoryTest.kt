package com.mrezanasirloo.venues.map.datasource

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


internal class VenuesRepositoryTest {

    private lateinit var mockApi: VenuesApi
    private lateinit var venuesRepository: VenuesRepository

    private val response1 = VenuesResponse(
        Response(
            listOf(
                VenuesItem(id = "1", name = "foo", location = Location("", 35.691, 51.319, 0, "")),
                VenuesItem(id = "2", name = "foo", location = Location("", 35.691, 51.319, 0, "")),
                VenuesItem(id = "3", name = "foo", location = Location("", 35.691, 51.319, 0, "")),
            )
        )
    )

    private val response2 = VenuesResponse(
        Response(
            listOf(
                VenuesItem(id = "4", name = "foo", location = Location("", 35.691, 51.319, 0, "")),
                VenuesItem(id = "5", name = "foo", location = Location("", 35.691, 51.319, 0, "")),
                VenuesItem(id = "6", name = "foo", location = Location("", 35.691, 51.319, 0, "")),
                VenuesItem(id = "7", name = "foo", location = Location("", 35.691, 51.319, 0, "")),
            )
        )
    )

    @Test
    fun `should put data in the cache`() {
        mockApi = mock {
            on { search(any(), any(), any(), any(), any()) } doReturn Single.just(response1)
        }
        val venuesStore = VenuesStore()
        venuesRepository = VenuesRepository(
            api = mockApi,
            venuesStore = venuesStore,
        )

        venuesRepository.search(
            LatLng(0.0, 0.0), LatLngBounds(
                LatLng(35.678, 51.283),
                LatLng(35.699, 51.337)
            )
        ).subscribe()

        val set = venuesStore.venues().blockingFirst()

        Assert.assertEquals(response1.response.venues, set.toList())
    }

    @Test
    fun `should return cached data when the cache is not empty`() {
        mockApi = mock {
            on { search(any(), any(), any(), any(), any()) } doReturn Single.just(response2)
        }

        venuesRepository = VenuesRepository(
            api = mockApi,
            venuesStore = VenuesStore().apply {
                add(response1.response.venues)
            },
        )

        val testObserver = TestObserver<List<VenuesItem>>()
        val ll = LatLng(0.0, 0.0)
        val bounds = LatLngBounds(
            LatLng(35.678, 51.283),
            LatLng(35.699, 51.337)
        )
        venuesRepository.venues(bounds).subscribe(testObserver)

        venuesRepository.search(ll, bounds).subscribe()

        testObserver.assertValueCount(2)
        testObserver.assertValueAt(0) {
            it.size == 3 && it == response1.response.venues
        }
        testObserver.assertValueAt(1) {
            it.size == 7 && it == response1.response.venues + response2.response.venues
        }
    }
}