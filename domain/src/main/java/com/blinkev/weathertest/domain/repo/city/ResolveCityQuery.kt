package com.blinkev.weathertest.domain.repo.city

import com.blinkev.weathertest.domain.entity.City
import io.reactivex.Observable

interface ResolveCityQuery {
    fun resolve(lat: Double, lon: Double): Observable<City>
}