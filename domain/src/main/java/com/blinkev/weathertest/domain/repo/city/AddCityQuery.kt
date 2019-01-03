package com.blinkev.weathertest.domain.repo.city

import com.blinkev.weathertest.domain.entity.City
import io.reactivex.Observable

interface AddCityQuery {
    fun add(city: City): Observable<Unit>
}