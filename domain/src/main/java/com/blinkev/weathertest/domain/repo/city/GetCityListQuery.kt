package com.blinkev.weathertest.domain.repo.city

import com.blinkev.weathertest.domain.entity.City
import io.reactivex.Observable

interface GetCityListQuery {
    fun get(): Observable<List<City>>
}