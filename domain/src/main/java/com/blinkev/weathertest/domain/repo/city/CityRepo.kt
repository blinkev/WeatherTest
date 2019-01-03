package com.blinkev.weathertest.domain.repo.city

import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.domain.entity.City
import io.reactivex.Observable

interface CityRepo {
    fun getAll(): Observable<DataStatus<List<City>>>
    fun addCity(city: City): Observable<DataStatus<Unit>>
    fun removeCity(city: City): Observable<DataStatus<Unit>>
}