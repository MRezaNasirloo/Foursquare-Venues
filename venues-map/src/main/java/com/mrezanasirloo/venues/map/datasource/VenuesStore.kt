package com.mrezanasirloo.venues.map.datasource

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * A cache for storing venues, which returns its data upon subscription and
 * subsequent updates.
 *
 * This can be further improved by using Generics and Abstraction to support
 * other data types and other cache mechanism
 * ```
 * interface Store<T> {
 *    fun add(data T);
 *    fun observe() : Observable<T>
 * }
 * ```
 */
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