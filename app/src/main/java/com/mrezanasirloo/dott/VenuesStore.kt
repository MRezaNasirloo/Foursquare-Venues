package com.mrezanasirloo.dott

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class VenuesStore @Inject constructor() {
    private val cache = HashSet<VenuesItem>(100)
    private val subject = BehaviorSubject.create<HashSet<VenuesItem>>()

    fun add(venues: Collection<VenuesItem>) {
        cache.addAll(venues)
        subject.onNext(cache)
    }

    fun venues(): Observable<HashSet<VenuesItem>> {
        return subject
    }
}